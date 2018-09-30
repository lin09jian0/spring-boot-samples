package com.jim.spring.boot.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jim lin
 * 2018/9/28.
 */
@SpringBootApplication
@Slf4j
@EnableScheduling
public class MetricsBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(MetricsBootstrap.class,args);
    }

}
