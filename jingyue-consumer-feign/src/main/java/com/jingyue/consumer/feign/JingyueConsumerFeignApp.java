package com.jingyue.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *  *自定义负载均衡的类不能再主启动类包及子包下
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.jingyue.api.service"})
public class JingyueConsumerFeignApp {
    public static void main(String[] args) {
        SpringApplication.run(JingyueConsumerFeignApp.class,args);
    }
}
