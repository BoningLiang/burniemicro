package edu.auburn.bzl0048.metro.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liangboning
 * @date 2019/7/22 17:45
 */
@Configuration
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = HttpServletRequest.class.cast(servletRequest);
        HttpServletResponse httpServletResponse = HttpServletResponse.class.cast(servletResponse);
        Set<String> allowOrigins = new HashSet<>(Arrays.asList(
                "http://localhost:8081",
                "http://localhost:8082",
                "http://localhost:8083",
                "http://localhost:8084",
                "http://localhost:8085"));
        Enumeration<String> origins = httpServletRequest.getHeaders("Origin");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        if (origins.hasMoreElements()) {
            String origin = origins.nextElement();
            if (allowOrigins.contains(origin)) {
                httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
