package com.bin.spring;

import com.bin.spring.testDI.CDPlay;
import com.bin.spring.testDI.CDPlayerConfig;
import com.bin.spring.testDI.SgtPeppers;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan(basePackages = "testDI")
public class SpringContextTest {
    public static void main(String[] args) throws InterruptedException {
//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/beans.xml");
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(CDPlayerConfig.class);
        System.out.println(acac.containsBean("sgtPeppers"));
        CDPlay play = new CDPlay();
        play.ac();
    }
}
