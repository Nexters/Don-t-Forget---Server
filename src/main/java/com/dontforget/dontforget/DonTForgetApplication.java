package com.dontforget.dontforget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.dontforget")
public class DonTForgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonTForgetApplication.class, args);
    }

}
