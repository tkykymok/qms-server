package com.qms.mainservice.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
public class ApplicationProperties {

    private final Environment env;

    public ApplicationProperties(Environment env) {
        this.env = env;
    }

    public String get(String key) {
        return env.getProperty(key);
    }
}
