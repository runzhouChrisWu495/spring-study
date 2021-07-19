package com.bin.entity;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class Cat implements InitializingBean, DisposableBean {
    Object o;

    public Cat() {
        System.out.println("miao");
        o = new Object();
        System.out.println(o);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }
}

@Aspect
@Component
class MyAop{
    @Pointcut("execution (public int com.bin.entity.Cat.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void log(){
        System.out.println("log start");
    }

    @After("pointcut()")
    public void logAfter(){
        System.out.println("log after");
    }
}
