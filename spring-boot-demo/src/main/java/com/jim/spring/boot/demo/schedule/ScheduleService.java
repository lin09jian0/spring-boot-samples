package com.jim.spring.boot.demo.schedule;

import com.jim.spring.boot.demo.client.RecordingClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jim lin
 * 2018/10/31.
 */
@Service
@Slf4j
public class ScheduleService {

    @Resource
    private RecordingClient client;

    @Scheduled(cron = "0 0/3 * * * ?")
    public void process(){
        log.info("================开始执行定时任务============");
        client.pullRecordingFromRongLian();
    }
}
