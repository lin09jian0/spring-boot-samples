package com.jim.spring.boot.spider.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jim lin
 * @date 2018/6/8.
 *         错误码枚举类
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    SYSTEM_ERROR("SYSTEM_ERROR","系统异常！"),
    ILLEGALITY_REQUEST("ILLEGALITY_REQUEST","非法请求！"),
    DB_ERROR("DB_ERROR","数据库异常！"),
    CITY_INFO_NOT_EXIST("CITY_INFO_NOT_EXIST","城市信息不存在！"),

    //
    ;
    String code;
    String message;

}
