package com.example.tourservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration

public class TourServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourServiceApplication.class, args);
    }

}
