package edu.auburn.bzl0048.metro.controller;

import com.cjbdi.leian.common.core.entity.Resp;
import com.cjbdi.leian.common.wsqs.service.SearchService;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author liangboning
 * @date 2019/7/25 16:20
 */

@RestController
public class SpringbootController {
    @Autowired
    private DiscoveryClient client;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private SearchService searchService;

//    private final Logger logger = Logger.getLogger(SpringbootController.class);

    private final Logger logger = Logger.getLogger("SpringbootController");

    @GetMapping("/hello")
    public String hello() {


        EurekaClientConfig eurekaClientConfig = eurekaClient.getEurekaClientConfig();
        logger.info("/hello dns name: " + eurekaClientConfig.getEurekaServerDNSName());
        return "hello";
    }

    @PostMapping(value = {"/analyze"})
    @ApiOperation(value = "分词")
    public Resp analyze(@RequestParam String analyzer, @RequestParam String content) {
        return new Resp<List<String>>(searchService.analyze(analyzer, content));
    }
}
