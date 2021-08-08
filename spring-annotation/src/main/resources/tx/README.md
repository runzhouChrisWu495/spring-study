# 1.事务管理
## 1.导入依赖
    数据源、数据库驱动、数据库操作
## 2.配置数据源
## 3.给方法上添加上 @Transactional 表明当前方法时一个事务方法
## 4.给配置类添加上 @EnableTransactionManagement 开启基于注解的事务管理功能
## 5.配置事务管理器来控制事务
    @Bean
    public PlantformTransactionManager transactionManager()
    
## 6原理
    1.利用 @EnableTransactionManagement 中的 TransactionManagementConfigurationSelector 向容器中导入一些组件
        根据 mode 的不同会导入不同的组件，默认是 PROXY
            如果是 PROXY 则导入 AutoProxyRegistrar、ProxyTransactionManagementConfiguration
            如果是 ASPECTJ 则导入 determineTransactionAspectClass
        1. AutoProxyRegistrar 给容器中注入 InfrastructureAdvisorAutoProxyCreator（和AOP的一样，也是一个后置处理器）
            利用后置处理器机制，在对象创建以后包装对象，返回一个代理对象（增强器），代理对象执行方法，利用拦截器链进行调用
            拦截器在执行的时候:
```shell script
    @Nullable
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null;
        return this.invokeWithinTransaction(invocation.getMethod(), targetClass, new CoroutinesInvocationCallback() {   // 在这里会执行拦截器的invokeWithinTransaction()
            @Nullable
            public Object proceedWithInvocation() throws Throwable {
                return invocation.proceed();
            }

            public Object getTarget() {
                return invocation.getThis();
            }

            public Object[] getArguments() {
                return invocation.getArguments();
            }
        });
    }
```
            拦截器的invokeWithinTransaction():
```shell script
	protected Object invokeWithinTransaction(Method method, @Nullable Class<?> targetClass,
			final InvocationCallback invocation) throws Throwable {

		// If the transaction attribute is null, the method is non-transactional.
		TransactionAttributeSource tas = getTransactionAttributeSource();
		final TransactionAttribute txAttr = (tas != null ? tas.getTransactionAttribute(method, targetClass) : null);
		final TransactionManager tm = determineTransactionManager(txAttr);     // 在这里会决定使用哪个事务管理器

		if (this.reactiveAdapterRegistry != null && tm instanceof ReactiveTransactionManager) {
			boolean isSuspendingFunction = KotlinDetector.isSuspendingFunction(method);
			boolean hasSuspendingFlowReturnType = isSuspendingFunction &&
					COROUTINES_FLOW_CLASS_NAME.equals(new MethodParameter(method, -1).getParameterType().getName());
			if (isSuspendingFunction && !(invocation instanceof CoroutinesInvocationCallback)) {
				throw new IllegalStateException("Coroutines invocation not supported: " + method);
			}
			CoroutinesInvocationCallback corInv = (isSuspendingFunction ? (CoroutinesInvocationCallback) invocation : null);

			ReactiveTransactionSupport txSupport = this.transactionSupportCache.computeIfAbsent(method, key -> {
				Class<?> reactiveType =
						(isSuspendingFunction ? (hasSuspendingFlowReturnType ? Flux.class : Mono.class) : method.getReturnType());
				ReactiveAdapter adapter = this.reactiveAdapterRegistry.getAdapter(reactiveType);
				if (adapter == null) {
					throw new IllegalStateException("Cannot apply reactive transaction to non-reactive return type: " +
							method.getReturnType());
				}
				return new ReactiveTransactionSupport(adapter);
			});

			InvocationCallback callback = invocation;
			if (corInv != null) {
				callback = () -> CoroutinesUtils.invokeSuspendingFunction(method, corInv.getTarget(), corInv.getArguments());
			}
			Object result = txSupport.invokeWithinTransaction(method, targetClass, callback, txAttr, (ReactiveTransactionManager) tm);
			if (corInv != null) {
				Publisher<?> pr = (Publisher<?>) result;
				return (hasSuspendingFlowReturnType ? KotlinDelegate.asFlow(pr) :
						KotlinDelegate.awaitSingleOrNull(pr, corInv.getContinuation()));
			}
			return result;
		}

		PlatformTransactionManager ptm = asPlatformTransactionManager(tm);
		final String joinpointIdentification = methodIdentification(method, targetClass, txAttr);

		if (txAttr == null || !(ptm instanceof CallbackPreferringPlatformTransactionManager)) {
			// Standard transaction demarcation with getTransaction and commit/rollback calls.
			TransactionInfo txInfo = createTransactionIfNecessary(ptm, txAttr, joinpointIdentification);    // 如果必要的话在这里开启事务,这里主要完成:1.关闭autocommi开启事务,2.根据事务传播行为，决定事务类型

			Object retVal;
			try {
				// This is an around advice: Invoke the next interceptor in the chain.
				// This will normally result in a target object being invoked.
				retVal = invocation.proceedWithInvocation();      // 在这里执行被代理对象原本的方法
			}
			catch (Throwable ex) {
				// target invocation exception
				completeTransactionAfterThrowing(txInfo, ex);   // 如果捕获了异常,会在这个方法中通过事务管理器进行回滚
				throw ex;
			}
			finally {
				cleanupTransactionInfo(txInfo);
			}

			if (retVal != null && vavrPresent && VavrDelegate.isVavrTry(retVal)) {
				// Set rollback-only in case of Vavr failure matching our rollback rules...
				TransactionStatus status = txInfo.getTransactionStatus();
				if (status != null && txAttr != null) {
					retVal = VavrDelegate.evaluateTryFailure(retVal, txAttr, status);
				}
			}

			commitTransactionAfterReturning(txInfo);    // 如果没有报错,在这里就会通过事务管理器提交事务
			return retVal;
		}
```
            createTransactionIfNecessary():
```shell script
	protected TransactionInfo createTransactionIfNecessary(@Nullable PlatformTransactionManager tm,
			@Nullable TransactionAttribute txAttr, final String joinpointIdentification) {

		// If no name specified, apply method identification as transaction name.
		if (txAttr != null && txAttr.getName() == null) {
			txAttr = new DelegatingTransactionAttribute(txAttr) {
				@Override
				public String getName() {
					return joinpointIdentification;
				}
			};
		}

		TransactionStatus status = null;
		if (txAttr != null) {
			if (tm != null) {
				status = tm.getTransaction(txAttr);   //尝试开启事务
			}
			else {
				if (logger.isDebugEnabled()) {
					logger.debug("Skipping transactional joinpoint [" + joinpointIdentification +
							"] because no transaction manager has been configured");
				}
			}
		}
		return prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
	}
```
            getTransaction():
```shell script
	@Override
	public final TransactionStatus getTransaction(@Nullable TransactionDefinition definition)
			throws TransactionException {

		// Use defaults if no transaction definition given.
		TransactionDefinition def = (definition != null ? definition : TransactionDefinition.withDefaults());

		Object transaction = doGetTransaction();    // 关闭autocommit,代表事务正式开启
		boolean debugEnabled = logger.isDebugEnabled();

		if (isExistingTransaction(transaction)) {
			// Existing transaction found -> check propagation behavior to find out how to behave.
			return handleExistingTransaction(def, transaction, debugEnabled);
		}

		// Check definition settings for new transaction.
		if (def.getTimeout() < TransactionDefinition.TIMEOUT_DEFAULT) {
			throw new InvalidTimeoutException("Invalid transaction timeout", def.getTimeout());
		}

		// No existing transaction found -> check propagation behavior to find out how to proceed.    // 通过事务传播行为决定当前的事务的类型
		if (def.getPropagationBehavior() == TransactionDefinition.PROPAGATION_MANDATORY) {
			throw new IllegalTransactionStateException(
					"No existing transaction found for transaction marked with propagation 'mandatory'");
		}
		else if (def.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRED ||
				def.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW ||
				def.getPropagationBehavior() == TransactionDefinition.PROPAGATION_NESTED) {
			SuspendedResourcesHolder suspendedResources = suspend(null);
			if (debugEnabled) {
				logger.debug("Creating new transaction with name [" + def.getName() + "]: " + def);
			}
			try {
				return startTransaction(def, transaction, debugEnabled, suspendedResources);
			}
			catch (RuntimeException | Error ex) {
				resume(null, suspendedResources);
				throw ex;
			}
		}
		else {
			// Create "empty" transaction: no actual transaction, but potentially synchronization.
			if (def.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT && logger.isWarnEnabled()) {
				logger.warn("Custom isolation level specified but no actual transaction initiated; " +
						"isolation level will effectively be ignored: " + def);
			}
			boolean newSynchronization = (getTransactionSynchronization() == SYNCHRONIZATION_ALWAYS);
			return prepareTransactionStatus(def, null, true, newSynchronization, debugEnabled, null);
		}
	}
```

        2. ProxyTransactionManagementConfiguration 
            1.给容器中注册事务增强器 
                1.事务增强器要用事务注解的信息 AnnotationTransactionAttributeSource 解析事务注解中的方法
                2.事务拦截器 TransactionInterceptor 保存了事务属性信息，事务管理器
                    它是一个 MethodInterceptor
                    在目标方法执行的时候
                        执行拦截器链
                        事务拦截器
                            1.先获取事务相关属性
                            2，再获取PlatformTransactionManager，如果没有事先添加，则从容器中取。（可以在@Transactional注解中指定事务管理器）
                            3，执行目标方法
                                如果异常，获取到事务管理器，利用事务管理器回滚
                                如果正常，利用事务管理器，提交事务
                