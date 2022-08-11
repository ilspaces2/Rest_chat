package ru.job4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Job4jChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(Job4jChatApplication.class, args);
        System.out.println("Loaded http://localhost:8080/");
    }
}
