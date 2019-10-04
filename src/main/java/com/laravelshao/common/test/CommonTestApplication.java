package com.laravelshao.common.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.laravelshao.common.test.mapper")
public class CommonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonTestApplication.class, args);
    }
}
