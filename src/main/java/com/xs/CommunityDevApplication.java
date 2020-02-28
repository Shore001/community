package com.xs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xs.mapper")
@SpringBootApplication
public class CommunityDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityDevApplication.class, args);
    }

}
