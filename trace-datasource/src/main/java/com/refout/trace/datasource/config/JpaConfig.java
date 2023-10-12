package com.refout.trace.datasource.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.refout.trace")
@EntityScan(basePackages = "com.refout.trace")
public class JpaConfig {

}
