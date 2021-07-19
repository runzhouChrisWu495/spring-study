package com.bin.spring.testDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DVDPlayer implements CompactDisc{

    @Autowired
    CDPlay cdPlay;

    public void play() {
        System.out.println("DVDPlayer");
    }

}

