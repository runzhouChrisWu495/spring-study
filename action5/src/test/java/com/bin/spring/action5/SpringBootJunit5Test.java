package com.bin.spring.action5;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootJunit5Test {
    @Test
    @DisplayName("第一个测试")
    public void test1(){
        Assertions.assertEquals(5,cal(1,2));
    }

    int cal(int i,int j){
        return i+j;
    }

    @Test
    public void test2(){
        Assertions.assertEquals(4,cal(1,2));
    }
}
