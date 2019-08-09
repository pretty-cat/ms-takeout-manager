package com.qf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class TakeoutZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeoutZuulApplication.class, args);
    }
}
