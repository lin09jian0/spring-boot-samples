package com.jim.spring.boot.spider.po;

import lombok.Data;

import java.util.Date;

/**
 * @author jim lin
 * @date 2019/2/27.
 */
@Data
public class CarDetailPO {

    private Integer id;
    /** 车辆ID  */
    private Integer carId;
    /** 汽车之家ID  */
    private String carInfoId;
    /** 年检到期  */
    private String annualSurveyDate;
    /** 保险到期  */
    private String insuranceDate;
    /** 质保到期  */
    private String guaranteeExpires;
    /** 排放标准  */
    private String emissionStandard;
    /** 过户次数  */
    private String transferCount;
    /** 用　　途  */
    private String use;
    /** 维修保养  */
    private String maintain;
    /** 商家名称  */
    private String dealerName;
    /** 商家手机号  */
//    private String dealerPhone;
    /** 汽车描述  */
    private String carDesc;
    /** 发 动 机  */
    private String engine;
    /** 变 速 器  */
    private String transmission;
    /** 车辆级别  */
    private String vehicleLevel;
    /** 颜　　色  */
    private String color;
    /** 燃油标号  */
    private String fuelLabel;
    /** 驱动方式  */
    private String driveMode;
    /** 创建时间   */
    private Date createAt;
    /** 修改时间   */
    private Date updateAt;
}
