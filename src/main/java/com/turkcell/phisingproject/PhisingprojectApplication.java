package com.turkcell.phisingproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhisingprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhisingprojectApplication.class, args);
    }

}
