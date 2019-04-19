package com.jim.spring.boot.spider.po;

import lombok.Data;

import java.util.Date;

/**
 * @author jim lin
 * @date 2019/2/27.
 */
@Data
public class CarImagePO {

    private Integer id;
    /** 车辆ID  */
    private Integer carId;
    /** 汽车之家ID  */
    private String carInfoId;
    /** 图片地址  */
    private String imageUrl;
    /** 创建时间   */
    private Date createAt;
    /** 修改时间   */
    private Date updateAt;
}
