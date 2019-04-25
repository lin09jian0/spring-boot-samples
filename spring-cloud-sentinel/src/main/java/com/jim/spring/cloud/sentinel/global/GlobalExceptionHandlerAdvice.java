package com.jim.spring.cloud.sentinel.global;

import com.jim.spring.cloud.sentinel.dto.RespDTO;
import com.jim.spring.cloud.sentinel.exception.MyBlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author jim lin
 * @date 2019/4/24.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = MyBlockException.class)
    public RespDTO blockExceptionHandler(MyBlockException ex){
        log.info("sentinel occur exception ：{}",ex.toString());
        return RespDTO.failure("系统太热了，降降火");
    }
}
