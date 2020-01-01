package com.jingyue.consumer.ribbon;

import com.jingyue.consumer.balance.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 *  *自定义负载均衡的类不能再主启动类包及子包下
 *  application.yml配置日志级别
 *  logging:
 *   pattern:
 *     console: '%d -%msg%n'
 *   path: D:/data/log/seller.log
 *   level:
 *     root: info
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@RibbonClient(name = "JINGYUE-DEPT"/*,configuration = MyRule.class*/)
public class JingyueConsumerRibbonApp {
    public static void main(String[] args) {
        SpringApplication.run(JingyueConsumerRibbonApp.class,args);
    }
}
