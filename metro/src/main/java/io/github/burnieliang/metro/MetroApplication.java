package io.github.burnieliang.metro;

import io.github.burnieliang.swagger.annotation.EnableMySwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liangboning
 * @date 2019/7/26 10:26
 */

@EnableMySwagger2
//@EnableEurekaClient
@SpringBootApplication
@MapperScan("io.github.burnieliang.metro.mapper")
public class MetroApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetroApplication.class, args);
    }
}
