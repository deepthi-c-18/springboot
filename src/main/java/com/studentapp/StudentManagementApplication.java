package com.studentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Application Main Entry Point
 * Student Management System - A complete DevOps CI/CD project
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.studentapp")
public class StudentManagementApplication {

    /**
     * Main method to start the Spring Boot application
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }
}
