package com.jim.spring.boot.spider.po;

import lombok.Data;

import java.util.Date;

/**
 * @author jim lin
 * @date 2019-02-20 14:50:54
 */
@Data
public class CarCityPO {
  private Integer id;
  private String code;
  private String parentCode;
  private String name;
  private String nameSpelling;
  private String partUrl;
  private String listKey;
  private Date createAt;
  private Date updateAt;

}
