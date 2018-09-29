package com.jim.spring.boot.metrics;

import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.cumulative.CumulativeTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.PrintStream;

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
