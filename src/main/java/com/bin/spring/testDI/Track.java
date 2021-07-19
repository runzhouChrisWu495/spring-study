package com.bin.spring.testDI;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Track {
    String[] tracks = new String[]{"Hello","It's me"};
}
