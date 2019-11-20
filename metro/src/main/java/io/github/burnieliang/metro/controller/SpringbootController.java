package io.github.burnieliang.metro.controller;


//import com.netflix.discovery.EurekaClient;
//import com.netflix.discovery.EurekaClientConfig;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author liangboning
 * @date 2019/7/25 16:20
 */

@RestController
public class SpringbootController {
//    @Autowired
//    private DiscoveryClient client;
//
//    @Autowired
//    private EurekaClient eurekaClient;


//    private final Logger logger = Logger.getLogger(SpringbootController.class);

    private final Logger logger = Logger.getLogger("SpringbootController");

    @GetMapping("/hello")
    public String hello() {


//        EurekaClientConfig eurekaClientConfig = eurekaClient.getEurekaClientConfig();
//        logger.info("/hello dns name: " + eurekaClientConfig.getEurekaServerDNSName());
        return "hello";
    }
}
