package com.jim.spring.cloud.sentinel.dto;

import lombok.Data;

/**
 * @author jim lin
 * @date 2019/4/24.
 */
@Data
public class RespDTO<T> {

    private int code;

    private String errorMsg;

    private T data;

    public static <T> RespDTO<T> success(T data){
        RespDTO<T> result = new RespDTO<>();
        result.setCode(0);
        result.setData(data);
        return result;
    }

    public static <T> RespDTO<T> failure(String errorMsg){
        RespDTO<T> result = new RespDTO<>();
        result.setCode(1);
        result.setErrorMsg(errorMsg);
        return result;
    }
}
