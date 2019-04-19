package com.jim.spring.boot.spider.mapper;

import com.jim.spring.boot.spider.po.CarInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarInfoMapper {

    CarInfoPO get(Integer id);

    CarInfoPO getByCarInfoId(String carInfoId);

    int add(CarInfoPO carInfoPO);

    int update(CarInfoPO carInfoPO);

    int updateByInfoId(CarInfoPO carInfoPO);

    int updateIgnoreNull(CarInfoPO carInfoPO);

    int batchAdd(@Param("carInfoPOList") List<CarInfoPO> carInfoPOList);

    Integer selectCount();

    List<CarInfoPO> selectByPage(@Param("pageNo") int pageNo,@Param("pageSize")int pageSize);

    int soldOut(@Param("soldOut")Boolean soldOut,@Param("id")Integer id);
}