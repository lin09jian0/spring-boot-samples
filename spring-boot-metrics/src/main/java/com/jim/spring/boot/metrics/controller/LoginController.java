package com.jim.spring.boot.metrics.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author jim lin
 * 2018/9/28.
 */
@RestController
public class LoginController {


    @Autowired
    private Timer timer;

    @RequestMapping("hello")
    @Timed(value = "hello",longTask = true,percentiles={0.9,0.99,0.9999},histogram = true)
    public String hello(){
        return "hello world!!";
    }
    @RequestMapping("hello2")
    public String hello2(){
        try {
            long start = System.currentTimeMillis();
            Thread.sleep(new Random().nextInt(1000));
            long end = System.currentTimeMillis();
            System.out.println("=============="+(end -start));
            timer.record(end -start,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello2 world!!";
    }
}
