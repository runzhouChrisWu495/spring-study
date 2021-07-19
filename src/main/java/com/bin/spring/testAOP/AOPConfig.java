package com.bin.spring.testAOP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy     // 启动自动代理功能，不开启则不会将带有@Aspect注解的类视为切面
public class AOPConfig {

    @Bean
    public Audience audience(){
        return new Audience();
    }
}
