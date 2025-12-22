package com.internshipProject.ESS_AI_project;

import com.internshipProject.ESS_AI_project.service.DataIngestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EssAiProjectApplication {

	public static void main(String[] args) {
        SpringApplication.run(EssAiProjectApplication.class, args);
	}
    @Bean
    public CommandLineRunner run(DataIngestionService service) {
        return args -> {
            service.processAndCreateCsv();
            System.exit(0);
        };
    }

}
