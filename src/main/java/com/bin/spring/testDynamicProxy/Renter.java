package com.bin.spring.testDynamicProxy;



import com.bin.spring.testAOP.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Renter implements Rent {
    String name;

    public Renter(String name) {
        this.name = name;
    }

    public void rent() {
        System.out.println(name+" is rent house");
    }


}
