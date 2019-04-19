package com.jim.spring.boot.spider.dto;

import lombok.Data;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@Data
public class RespDTO<T> {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    private T obj;
}
