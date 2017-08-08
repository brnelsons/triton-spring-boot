package com.bnelson.triton.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TritonBootApp {
    public static void main(String[] args) {
        SpringApplication.run(TritonBootApp.class, args);
    }
}
