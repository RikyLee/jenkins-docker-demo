package com.rikylee.jenkinsdockerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;

@SpringBootApplication
public class JenkinsDockerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JenkinsDockerDemoApplication.class, args);
    }
}
