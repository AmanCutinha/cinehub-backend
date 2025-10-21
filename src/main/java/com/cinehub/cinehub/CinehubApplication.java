package com.cinehub.cinehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cinehub")
public class CinehubApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinehubApplication.class, args);
        System.out.println("✅ CineHub backend started successfully!");
    }
}
