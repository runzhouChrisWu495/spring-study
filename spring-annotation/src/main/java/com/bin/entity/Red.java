package com.bin.entity;

import com.bin.config.MyConfig;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class Red implements BeanNameAware{

    @Override
    public void setBeanName(String name) {
        System.out.println("当前Bean的名字"+name);
    }
}

@Component
class Blue implements EmbeddedValueResolverAware{
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String s = resolver.resolveStringValue("你好${os.name}我是#{20*20}");
        System.out.println(s);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}