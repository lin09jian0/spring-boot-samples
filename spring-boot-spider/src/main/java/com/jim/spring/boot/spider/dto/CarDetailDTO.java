package com.jim.spring.boot.spider.dto;

import com.jim.spring.boot.spider.po.CarDetailPO;
import com.jim.spring.boot.spider.po.CarImagePO;
import lombok.Data;

import java.util.List;

/**
 * @author jim lin
 * @date 2019/2/27.
 */
@Data
public class CarDetailDTO {

    private CarDetailPO carDetailPO;

    private List<CarImagePO> carImagePOList;
}
