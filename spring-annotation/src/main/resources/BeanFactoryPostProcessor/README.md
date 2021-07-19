# 1.BeanFactoryPostProcessor
## 1.拓展原理
    BeanPostProcessor 后置处理器，在bean创建对象初始化前后进行拦截工作的
    BeanFactoryPostProcessor BeanFactory的后置处理器
        在 BeanFactory 标准初始化之后调用，在所有的 【bean的定义信息】 已经保存到beanFactory中，但还没有开始创建时调用
        
### 2.BeanPostProcessor 原理
    1.IOC容器refresh()
    2.invokeBeanFactoryPostProcessor（beanFactory）
        如何找到所有的BeanFactoryPostProcessor并执行它们的方法
            1.直接在BeanFactory中找到所有类型是 BeanFactoryPostProcessor 的组件，并执行他们的 postProcessBeanFactory 方法
            2.在初始化创建其他组件前面执行       
### 3.如何拓展
```java
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Object myConfig1 = beanFactory.getBean("myConfig1");
        System.out.println(myConfig1);
        System.out.println("MyBeanFactoryPostProcessor");
        int count = beanFactory.getBeanDefinitionCount();
        System.out.println("当前BeanFactory中有"+count+"个bean的定义信息");
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
```

    实现 BeanFactoryPostProcessor 接口，可以在 postProcessBeanFactory 方法中修改bean的定义属性，
        例如可以把bean的scope从singleton改为prototype，也可以把property的值给修改掉。
        可以同时配置多个BeanFactoryPostProcessor，并通过设置'order'属性来控制各个BeanFactoryPostProcessor的执行次序。
    【Mybatis与Spring集成中就用到了此接口，让mapper接口也可以被调用】