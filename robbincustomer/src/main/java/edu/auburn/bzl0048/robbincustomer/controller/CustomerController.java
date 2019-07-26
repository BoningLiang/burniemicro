package edu.auburn.bzl0048.robbincustomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liangboning
 * @date 2019/7/26 10:30
 */
@RestController
public class CustomerController {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @GetMapping("/ribbon-customer")
    public String helloCustomer() {
        return restTemplate.getForEntity("http://METRO/hello", String.class).getBody();
    }
}
