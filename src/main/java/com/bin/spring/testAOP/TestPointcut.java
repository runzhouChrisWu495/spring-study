package com.bin.spring.testAOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TestPointcut {
    @Before("performance()) ")      // Spring实战书中存在错误，此处并不是所有位置都可以使用
    public void silenceCel1Phones() {
        System.out.println("TestPointcut");
    }
}
