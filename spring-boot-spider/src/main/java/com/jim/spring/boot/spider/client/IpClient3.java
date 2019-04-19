package com.jim.spring.boot.spider.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@FeignClient(name = "ipClient",url = "http://123.58.6.163:3314")
public interface IpClient3 {

    /**
     * 从极光IP代理获取IP列表
     * @return IP列表
     */
    @GetMapping(value = "/Tools/proxyIP.ashx?OrderNumber=2eb4069494ac779da6b054fdcf10e57a&poolIndex=65096&cache=1&qty=20&Split=JSON2"
            )
    JSONObject getIpList();

}
