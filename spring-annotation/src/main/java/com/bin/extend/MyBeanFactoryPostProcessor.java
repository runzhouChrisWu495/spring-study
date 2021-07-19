package com.bin.extend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

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

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig1.class);

    }
}
