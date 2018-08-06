package com.littlefrog.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.littlefrog.controller" })
@EnableJpaRepositories(basePackages = {"com.littlefrog.respository"})
@EntityScan(basePackages = {"com.littlefrog.entity"})
@ComponentScan(basePackages = {"com.littlefrog.service"})
@SpringBootApplication
public class LifeScienceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeScienceApplication.class, args);
    }
}
