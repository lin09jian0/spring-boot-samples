package com.jim.spring.boot.spider.controller;

import com.jim.spring.boot.spider.service.CarHomeService;
import com.jim.spring.boot.spider.utils.HtmlParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@RestController
public class HelloController {

    @Resource
    private CarHomeService carHomeService;

    @GetMapping("hello")
    public void hello(){
        carHomeService.parseCarInfo();
    }

    @GetMapping("detail")
    public void detail(@RequestParam("startNum") Integer startNum,@RequestParam("endNum") Integer endNum){
        carHomeService.parseDetail(startNum,endNum);
    }
}
