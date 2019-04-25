package com.jim.spring.cloud.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jim.spring.cloud.sentinel.dto.RespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jim lin
 * @date 2019/4/24.
 */
@RestController
@Slf4j
public class HelloController {

    @RequestMapping("hello")
    public String hello(@RequestParam("name")String name){
        return "hello " + name;
    }

    // 定义自己的限流策略，使用当前类的“exceptionHandler”方法，如果加上blockHandlerClass属性，则表示是在blockHandlerClass类中的“exceptionHandler”方法
    // 此处value 会在sentinel的dashboard中生成一个hello3的链路，可以对此设置限流
    @SentinelResource(value = "hello3" , blockHandler = "exceptionHandler")
    @RequestMapping("hello2")
    public RespDTO<String> hello2(@RequestParam("name")String name){
        String data = "hello " + name;
        return RespDTO.success(data);
    }

    /**
     *  熔断  休眠 500ms
     */
    @RequestMapping("hello4")
    public RespDTO<String> hello4(@RequestParam("name")String name){
        String data = "hello " + name;
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("模拟异常");
    }

    public RespDTO<String> exceptionHandler(String name ,BlockException ex){
        String data = "hello " + name + " 你被限制请求了,请稍后再试";
        log.warn("sentinel :{}",ex.toString());
        return RespDTO.failure(data);
    }

}
