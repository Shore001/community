package com.xs;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@MapperScan("com.xs.mapper")
@SpringBootApplication
public class CommunityDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityDevApplication.class, args);
    }

}
