package com.bin.spring.action5;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)    // 和SpringJunit4ClassRunner是一样的，取这名是怕误解为只能用在Junit4上
class Action5ApplicationTests {
    @Autowired
    Car car;

    @Test
    void contextLoads() {
        System.out.println(car);
    }


}


