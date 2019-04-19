package com.jim.spring.boot.spider.mapper;

import com.jim.spring.boot.spider.po.CarDetailPO;

/**
 * @author jim lin
 * @date 2019/2/20.
 */
public interface CarDetailMapper {

    CarDetailPO get(Integer id);

    CarDetailPO selectByCarId(Integer carId);

    int add(CarDetailPO carDetailPO);

    int update(CarDetailPO carDetailPO);

    int updateIgnoreNull(CarDetailPO carDetailPO);

}