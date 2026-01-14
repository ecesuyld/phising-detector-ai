package com.turkcell.phisingproject;

import com.turkcell.phisingproject.service.DataProcessService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PhisingprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhisingprojectApplication.class, args);
    }

}
