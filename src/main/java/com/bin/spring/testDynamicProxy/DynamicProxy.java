package com.bin.spring.testDynamicProxy;
import com.bin.spring.testAOP.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {
    private Rent rent;

    public DynamicProxy(Rent rent) {
        this.rent = rent;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object invoke = method.invoke(rent, args);
        System.out.println("after");
        return invoke;
    }

}

