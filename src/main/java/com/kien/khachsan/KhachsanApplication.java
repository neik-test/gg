package com.kien.khachsan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.kien.khachsan")
@EnableJpaRepositories(basePackages = "com.kien.khachsan.repository")
public class KhachsanApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhachsanApplication.class, args);
        System.out.println("Backend started successfully!");
        System.out.println("API Docs: http://localhost:8080/swagger-ui.html");
        System.out.println("Health Check: http://localhost:8080/health");
    }
}