package com.sipc;

import lombok.extern.slf4j.Slf4j;
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
