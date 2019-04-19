package com.jim.spring.boot.spider.dto;

import lombok.Data;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@Data
public class RespDTO2<T> {


    private Integer code;

    private T result;
}
