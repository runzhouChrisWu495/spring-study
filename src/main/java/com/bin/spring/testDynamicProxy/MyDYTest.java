package com.bin.spring.testDynamicProxy;

import com.bin.spring.testAOP.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Observable;

public class MyDYTest {
    public static void main(String[] args) {
        Rent renter = new Renter("Jack");
        InvocationHandler proxy = new DynamicProxy(renter);
        Class<Renter> clazz = Renter.class;
        Rent Jack = (Rent) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),proxy);
        Jack.rent();
    }
}
