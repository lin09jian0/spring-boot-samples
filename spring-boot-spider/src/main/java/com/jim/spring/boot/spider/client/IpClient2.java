package com.jim.spring.boot.spider.client;

import com.alibaba.fastjson.JSONObject;
import com.jim.spring.boot.spider.dto.IpDTO;
import com.jim.spring.boot.spider.dto.RespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@FeignClient(name = "ipClient",url = "https://proxyapi.mimvp.com")
public interface IpClient2 {

    /**
     * 从极光IP代理获取IP列表
     * @return IP列表
     */
    @GetMapping(value = "/api/fetchopen.php?orderid=869212812036245397&num=100&http_type=2&anonymous=3,5&result_fields=1,2&result_format=json"
            )
    JSONObject getIpList();

}
