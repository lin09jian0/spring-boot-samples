package com.jim.spring.boot.spider.mapper;

import com.jim.spring.boot.spider.po.CarImagePO;

import java.util.List;

/**
 * @author jim lin
 * @date 2019/2/20.
 */
public interface CarImageMapper{

    CarImagePO get(Integer id);

    int add(CarImagePO carImagePO);

    int update(CarImagePO carImagePO);

    int updateIgnoreNull(CarImagePO carImagePO);

    List<CarImagePO> selectByCarId(Integer carId);

}