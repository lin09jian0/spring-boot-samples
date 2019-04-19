package com.jim.spring.cloud.slueth.controller;

import com.jim.spring.cloud.slueth.service.GeneralExecutorService1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jim lin
 * 2018/10/16.
 */
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private GeneralExecutorService1 executorService1;


    @RequestMapping("hello")
    public String hello(){
        log.info("hello world==========");
        executorService1.sayHello();
        return "hello world";
    }

    @RequestMapping("hello2")
    public String hello2(){
        log.info("hello2 world==========");
        executorService1.sayHello();
        return "hello2 world";
    }
}
