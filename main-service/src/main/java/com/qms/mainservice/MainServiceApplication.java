package com.qms.mainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@SpringBootApplication
@ComponentScan(basePackages = {"com.qms.mainservice", "com.qms.shared"})
public class MainServiceApplication {

    public static void main(String[] args) {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
        SpringApplication.run(MainServiceApplication.class, args);
    }

}
