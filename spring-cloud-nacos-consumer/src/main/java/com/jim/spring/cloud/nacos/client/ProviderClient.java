package com.jim.spring.cloud.nacos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jim lin
 * @date 2019/4/19.
 */
@FeignClient(name = "spring-cloud-nacos-provider")
public interface ProviderClient {

    @RequestMapping("hello")
    String hello(@RequestParam("name")String name);
}
