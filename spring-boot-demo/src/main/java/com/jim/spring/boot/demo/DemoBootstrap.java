package com.jim.spring.boot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jim lin
 * 2018/10/31.
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class DemoBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(DemoBootstrap.class,args);
    }
}
