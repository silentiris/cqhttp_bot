package com.sipc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class QqBotHttpApplication {
    public static void main(String[] args) {
        SpringApplication.run(QqBotHttpApplication.class, args);
    }

}
