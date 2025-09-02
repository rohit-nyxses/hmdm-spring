package com.hmdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.hmdm")
@ServletComponentScan(basePackages = "com.hmdm")
@MapperScan("com.hmdm.persistence.mapper")
public class HmdmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmdmServerApplication.class, args);
    }
}