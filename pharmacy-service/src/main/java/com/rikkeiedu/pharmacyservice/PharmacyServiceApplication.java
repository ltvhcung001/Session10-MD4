package com.rikkeiedu.pharmacyservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class PharmacyServiceApplication {

    @Value("${app.branch-name:Unknown Branch}")
    private String branchName;

    @Value("${app.hotline:Unknown Hotline}")
    private String hotline;

    public static void main(String[] args) {
        SpringApplication.run(PharmacyServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("=======================================================");
            System.out.println("Branch Name: " + branchName);
            System.out.println("Hotline: " + hotline);
            System.out.println("=======================================================");
        };
    }
}
