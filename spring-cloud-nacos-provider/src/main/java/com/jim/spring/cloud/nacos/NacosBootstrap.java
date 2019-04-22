package com.jim.spring.cloud.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jim lin
 * @date 2019/4/19.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(NacosBootstrap.class,args);
    }
}
