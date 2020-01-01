package com.jingyue.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * elasticseach 搜索服务项目启动类
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.jingyue.api.service"})
public class JingyueElasricsearchApp9001 {
    public static void main(String[] args) {
        SpringApplication.run(JingyueElasricsearchApp9001.class,args);
    }
}
