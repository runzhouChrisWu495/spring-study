package com.bin.spring.action5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@SpringBootApplication()
public class Action5Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Action5Application.class, args);
//        String[] names = context.getBeanDefinitionNames();
//        for (int i = 0; i < names.length; i++) {
//            System.out.println(names[i]);
//        }
//        System.out.println(names.length);
    }

}
