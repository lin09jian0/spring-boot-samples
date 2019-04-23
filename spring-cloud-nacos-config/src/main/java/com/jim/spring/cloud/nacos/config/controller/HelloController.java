package com.jim.spring.cloud.nacos.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jim lin
 * @date 2019/4/22.
 */
@RestController
@RefreshScope
public class HelloController {

    @Value("${config.name:}")
    private String name;

    @RequestMapping("hello")
    public String hello(){
        return "hello " + name;
    }
}
