package com.refout.trace.authentication;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootApplication(scanBasePackages = "com.refout")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
