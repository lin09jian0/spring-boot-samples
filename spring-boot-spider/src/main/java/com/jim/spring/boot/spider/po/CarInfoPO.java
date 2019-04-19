package com.jim.spring.boot.spider.po;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jim lin
 * @date 2019-02-20 14:50:54
 */
@Data
public class CarInfoPO {

  private Integer id;
  /** 车辆名称   */
  private String carName;
  /** 车价（万元）   */
  private BigDecimal carPrice;
  /** 公里数（万公里）   */
  private Double milleage;
  /** 首次上牌时间   */
  private String registerDate;
  /** 发布时间   */
  private LocalDateTime publicDate;
  /** 车辆信息ID（汽车之家ID）   */
  private String carInfoId;
  /** 门店ID（汽车之家门店ID）   */
  private String dealerId;
  /** 品牌   */
  private String branchId;
  /** 车系   */
  private String seriesId;
  /** 城市代码   */
  private String cityCode;
  /** 城市名称   */
  private String cityName;
  /** 父级城市代码   */
  private String parentCityCode;
  /** 车型   */
  private String modelId;
  /** 是否新车，true：是，false：否   */
  private Integer isNewCar;
  /** 是否延保，true：是，false：否   */
  private Integer isExtendRepair;
  /** 车辆来源，01：汽车之家   */
  private String source;
  /** 展示的缩略图URL   */
  private String imgUrl;
  /** 是否下架   */
  private boolean soldOut;
  /** 创建时间   */
  private Date createAt;
  /** 修改时间   */
  private Date updateAt;

}
