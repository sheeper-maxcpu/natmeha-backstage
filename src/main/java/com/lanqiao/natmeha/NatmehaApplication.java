package com.lanqiao.natmeha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.lanqiao.natmeha.dao")
public class NatmehaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NatmehaApplication.class, args);
    }

}
