package com.jim.spring.boot.spider.client;

import com.jim.spring.boot.spider.dto.IpDTO;
import com.jim.spring.boot.spider.dto.RespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@FeignClient(name = "ipClient",url = "http://www.xiongmaodaili.com")
public interface IpClient {

    /**
     * 从极光IP代理获取IP列表
     * @return IP列表
     */
    @GetMapping(value = "http://d.jghttp.golangapi.com/getip?num=4&type=2&pro=&city=0&yys=0&port=11&time=1&ts=1&ys=1&cs=1&lb=1&sb=0&pb=4&mr=1&regions="
            )
    RespDTO<List<IpDTO>> getIpList();

    /**
     * 熊猫IP代理
     * @return IP列表
     */
    @GetMapping(value = "/xiongmao-web/api/glip?secret=26240f4c2a0d3a7dbe8c032eb4db986d&orderNo=GL201903011051345TJK9HOh&count=20&isTxt=0&proxyType=1"
    )
    RespDTO<List<IpDTO>> getPandaIpList();

}
