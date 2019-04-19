package com.jim.spring.cloud.slueth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author jim lin
 * 2018/10/16.
 */
@Service
@Slf4j
public class GeneralExecutorService1 {

    @Autowired
    @Qualifier("threadPoolExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    public void sayHello(){
        threadPoolExecutor.execute(new GeneralRunnable());
        new Thread(new GeneralRunnable()).start();
    }

    class GeneralRunnable implements Runnable{

        @Override
        public void run() {
            log.info("I am general ThreadPoolExecutor");
        }
    }

}
