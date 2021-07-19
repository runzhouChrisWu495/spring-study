package com.bin.config;

import com.bin.entity.Cat;
import com.bin.entity.UserFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(value = "com.bin")
@EnableAspectJAutoProxy
public class MyConfig {

    @Bean
    UserFactoryBean userFactoryBean(){
        return new UserFactoryBean();
    }

    @Bean
    Cat cat(){
        return new Cat();
    }
}