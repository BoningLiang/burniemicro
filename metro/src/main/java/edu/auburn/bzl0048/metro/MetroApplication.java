package edu.auburn.bzl0048.metro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liangboning
 * @date 2019/7/26 10:26
 */

@EnableEurekaClient
@SpringBootApplication
public class MetroApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetroApplication.class, args);
    }
}
