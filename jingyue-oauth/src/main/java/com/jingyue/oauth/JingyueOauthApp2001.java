package com.jingyue.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class JingyueOauthApp2001 {
    public static void main(String[] args) {
        SpringApplication.run(JingyueOauthApp2001.class,args);

    }
}
