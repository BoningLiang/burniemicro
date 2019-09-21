package io.github.burnieliang.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author liangboning
 * @date 2019/7/26 14:46
 */
@EnableConfigServer
@SpringBootConfiguration
@EnableAutoConfiguration
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
