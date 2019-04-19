package com.jim.spring.cloud.slueth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jim lin
 * 2018/10/16.
 */
@Configuration
public class MyConfiguration {

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setKeepAliveSeconds(50);
        executor.setMaxPoolSize(10);
        return executor;
    }

    @Bean("threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,100,TimeUnit.MINUTES,new ArrayBlockingQueue(10));
//        executor.setCorePoolSize(5);
//        executor.setKeepAliveSeconds(50);
//        executor.setMaxPoolSize(10);
        return executor;
    }

}
