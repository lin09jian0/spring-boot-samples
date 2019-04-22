package com.jim.spring.cloud.nacos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jim lin
 * @date 2019/4/19.
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(String name){
        return "hello "+ name;
    }
}
