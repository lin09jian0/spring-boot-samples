package com.jim.spring.cloud.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jim lin
 * @date 2019/4/19.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ConsumerBootstrap {

    //如果集成nacos 你没有使用@EnableDiscoveryClient 则启动不了，这个和其他的注册中心有点区别
    public static void main(String[] args) {
        SpringApplication.run(ConsumerBootstrap.class,args);
    }
}
