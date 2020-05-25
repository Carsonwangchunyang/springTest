package com.carson.springtestdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.carson.springtestdemo.dao")
public class SpringtestdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtestdemoApplication.class, args);
    }

}
