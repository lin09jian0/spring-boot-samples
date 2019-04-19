package com.jim.spring.boot.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jim lin
 * 2018/10/31.
 */
@FeignClient(name = "recording",url = "http://baserecording.test.victorplus.cn/")
public interface RecordingClient {

    @GetMapping("/pullRecordingFromRongLian")
    void pullRecordingFromRongLian();
}
