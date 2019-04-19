package com.jim.spring.boot.spider.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@Data
public class IpDTO {

    /** IP  */
    private String ip;
    /** 端口号  */
    private Integer port;
    /** 过期时间  */
    private LocalDateTime expire_time;
    /** 城市  */
    private String city;
    /** 运营商  */
    private String isp;

    private String httpType;
}
