package com.bin.spring.testAOP;


import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;

@Component
public class Actress implements Performance{
    public void perform() {
        System.out.println("Actress is Performance");
    }

    public void rest(int n) {
        System.out.println("Actress is rest" + n + "seconds");
    }
}
