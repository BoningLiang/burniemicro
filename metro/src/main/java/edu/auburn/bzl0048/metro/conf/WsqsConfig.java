package edu.auburn.bzl0048.metro.conf;

import com.cjbdi.leian.common.wsqs.service.SearchService;
import com.cjbdi.leian.common.wsqs.service.impl.SearchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WsqsConfig {

    @Bean
    public SearchService searchService() {
        return new SearchServiceImpl();
    }

}
