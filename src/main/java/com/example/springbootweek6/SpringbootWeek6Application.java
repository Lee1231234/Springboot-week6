package com.example.springbootweek6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringbootWeek6Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWeek6Application.class, args);
    }

}
