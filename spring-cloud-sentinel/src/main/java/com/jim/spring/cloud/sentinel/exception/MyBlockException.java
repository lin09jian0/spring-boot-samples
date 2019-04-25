package com.jim.spring.cloud.sentinel.exception;

import lombok.Data;

/**
 * @author jim lin
 * @date 2019/4/24.
 */
public class MyBlockException extends RuntimeException{

    public MyBlockException(){
        super();
    }

    public MyBlockException(Throwable cause) {
        super(cause);
    }

    public MyBlockException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyBlockException(String message) {
        super(message);
    }
}
