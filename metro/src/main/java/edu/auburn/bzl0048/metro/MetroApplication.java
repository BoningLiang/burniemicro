package edu.auburn.bzl0048.metro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liangboning
 * @date 2019/7/26 10:26
 */

@EnableEurekaClient
@SpringBootApplication
@MapperScan("edu.auburn.bzl0048.metro.mapper")
public class MetroApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetroApplication.class, args);
    }
}
