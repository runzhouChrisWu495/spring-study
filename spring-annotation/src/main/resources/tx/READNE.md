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
        2. ProxyTransactionManagementConfiguration 
            1.给容器中注册事务增强器 
                1.事务增强器要用事务注解的信息 AnnotationTransactionAttributeSource 解析事务注解中的方法
                2.事务拦截器 
                    TransactionInterceptor 保存了事务属性信息，事务管理器
                    它是一个 MethodInterceptor
                    在目标方法执行的时候
                        执行拦截器链
                        事务拦截器
                            1.先获取事务相关属性
                            2，再获取PlatformTransactionManager，如果没有事先添加，则从容器中取。（可以在@Transactional注解中指定事务管理器）
                            3，执行目标方法
                                如果异常，获取到事务管理器，利用事务管理器回滚
                                如果正常，利用事务管理器，提交事务
                    