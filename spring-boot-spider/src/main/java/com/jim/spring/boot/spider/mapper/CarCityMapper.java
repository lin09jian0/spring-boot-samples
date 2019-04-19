package com.jim.spring.boot.spider.mapper;

import com.jim.spring.boot.spider.po.CarCityPO;

import java.util.List;

public interface CarCityMapper{

    CarCityPO get(Integer id);

    int add(CarCityPO carCityPO);

    int update(CarCityPO carCityPO);

    int updateIgnoreNull(CarCityPO carCityPO);

    List<CarCityPO> list();

}