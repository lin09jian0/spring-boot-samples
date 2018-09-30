package com.jim.spring.boot.metrics.service;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.cumulative.CumulativeTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * @author jim lin
 * 2018/9/29.
 */

@Component
@Slf4j
public class Service {

    @Autowired
    private CumulativeTimer timer;
    @Autowired
    private MeterRegistry meterRegistry;

    @Scheduled(cron = "0/5 * * * * *")
    public void consoleMeter(){
        while (true){
            try {
                if (null != timer.takeSnapshot()){
                    log.info("===consoleMeter====:{},:{}",timer.getId().getName(),timer.takeSnapshot().toString());
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Scheduled(fixedDelay=5000)
    public void consoleAllMeter(){
        while (true){
            try {
                meterRegistry.getMeters().stream().filter( n->n.getId().getName().equals("hello") && n instanceof CumulativeTimer).forEach(
                        meter -> {
                            CumulativeTimer cumulativeTimer = (CumulativeTimer)meter;
                            log.info("==+consoleAllMeter====:{},:{},:{}",cumulativeTimer.getId().getName(),cumulativeTimer.takeSnapshot().toString(),cumulativeTimer);
                        }
                );
//                log.info("===========:{}",count);
//                List<Meter> list = meterRegistry.getMeters();
//                for (Meter meter :list){
//                    if ("hello".equals(meter.getId().getName()) && meter instanceof CumulativeTimer){
//                        CumulativeTimer cumulativeTimer = (CumulativeTimer)meter;
//                        log.info("==+consoleAllMeter====:{},:{},:{}",cumulativeTimer.getId().getName(),cumulativeTimer.takeSnapshot().toString(),cumulativeTimer);
//                    }
//                }
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
