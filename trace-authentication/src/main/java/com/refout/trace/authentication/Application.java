package com.refout.trace.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.refout")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
