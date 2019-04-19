package com.jim.spring.boot.spider.dto;

import com.jim.spring.boot.spider.po.CarInfoPO;
import lombok.Data;

import java.util.List;

/**
 * @author jim lin
 * @date 2019/2/20.
 */
@Data
public class CarInfoDTO {

    private List<CarInfoPO> list;

    private Integer totalPage;
}
