package com.jim.spring.boot.spider.exception;

import com.jim.spring.boot.spider.enums.ErrorCodeEnum;

/**
 * @author jim lin
 * 2018/9/10.
 */
public class ServiceException extends RuntimeException{

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
