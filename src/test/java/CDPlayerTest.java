//package com.bin.spring.testDI;

import com.bin.spring.HelloWorld;
import com.bin.spring.testDI.CDPlay;
import com.bin.spring.testDI.CDPlayerConfig;
import com.bin.spring.testDI.CompactDisc;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= CDPlayerConfig.class)
public class CDPlayerTest {
    @Qualifier("DVDPlayer")
    @Autowired
    private CompactDisc cd;
    @Autowired
    private CDPlay cdPlay;
    @Autowired
    private CDPlayerConfig cdPlayerConfig;

    @Autowired
    HelloWorld helloWorld;

    @Test
    public void cdShouldNotBeNull() {
//        assertNotNull(cd);
        cd.play();
        System.out.println("A");
    }


    @Test
    public void testCDPlay(){
        cdPlay.ac();
//        cdPlay.setSt(new SgtPeppers("abin","hello"));
//        cdPlay.ac();
//        System.out.println(System.identityHashCode(cdPlay.st));
//        System.out.println(System.identityHashCode(cd));
//        helloWorld.hello();
    }
}


