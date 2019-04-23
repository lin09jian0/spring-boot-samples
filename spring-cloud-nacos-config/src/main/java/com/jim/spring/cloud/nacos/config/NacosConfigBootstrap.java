package com.jim.spring.cloud.nacos.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jim lin
 * @date 2019/4/22.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigBootstrap.class,args);
    }
}
