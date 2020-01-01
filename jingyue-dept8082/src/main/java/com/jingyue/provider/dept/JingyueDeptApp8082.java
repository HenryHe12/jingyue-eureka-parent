package com.jingyue.provider.dept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
/*@EnableCircuitBreaker*/
public class JingyueDeptApp8082 {
    public static void main(String[] args) {
        SpringApplication.run(JingyueDeptApp8082.class, args);
    }
}
