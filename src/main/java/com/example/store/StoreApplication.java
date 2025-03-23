package com.example.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("com.example.store.repository")
@EntityScan("com.example.store.model") // Đảm bảo JPA quét package chứa entity
@RestController
public class StoreApplication {
    public static void main(String[] args) {
      SpringApplication.run(StoreApplication.class, args);
    }
}