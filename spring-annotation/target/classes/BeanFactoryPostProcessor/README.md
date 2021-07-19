# 1.BeanFactoryPostProcessor
## 1.拓展原理
    BeanPostProcessor 后置处理器，bean创建对象初始化前后进行拦截工作的
    BeanFactoryPostProcessor BeanFactory的后置处理器
        在 BeanFactory 标准初始化之后调用，在所有的 bean定义 已经保存到beanFactory中，但还没有创建时调用
    