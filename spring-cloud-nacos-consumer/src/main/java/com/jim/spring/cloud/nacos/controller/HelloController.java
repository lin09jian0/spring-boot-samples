package com.jim.spring.cloud.nacos.controller;

import com.jim.spring.cloud.nacos.client.ProviderClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jim lin
 * @date 2019/4/19.
 */
@RestController
public class HelloController {

    @Resource
    private ProviderClient providerClient;

    @RequestMapping("hello")
    public String hello(@RequestParam("name")String name){
        return providerClient.hello(name);
    }
}
