package com.bin.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Data
public class User {
    private String name;
    private String password;
}
