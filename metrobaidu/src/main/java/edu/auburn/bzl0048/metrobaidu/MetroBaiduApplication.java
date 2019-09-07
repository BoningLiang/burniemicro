package edu.auburn.bzl0048.metrobaidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class MetroBaiduApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetroBaiduApplication.class, args);
    }
}
