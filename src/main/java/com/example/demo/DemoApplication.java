package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // Chỉ để lại đúng 1 dòng khởi động Spring Boot này thôi
        SpringApplication.run(DemoApplication.class, args);
    }

}