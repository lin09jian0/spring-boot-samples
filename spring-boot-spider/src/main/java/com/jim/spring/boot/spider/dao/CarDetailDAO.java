package com.jim.spring.boot.spider.dao;

import com.jim.spring.boot.spider.dto.CarDetailDTO;
import com.jim.spring.boot.spider.mapper.CarDetailMapper;
import com.jim.spring.boot.spider.mapper.CarImageMapper;
import com.jim.spring.boot.spider.po.CarDetailPO;
import com.jim.spring.boot.spider.po.CarImagePO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jim lin
 * @date 2019/2/27.
 */
@Service
public class CarDetailDAO {

    @Resource
    private CarDetailMapper carDetailMapper;

    @Resource
    private CarImageMapper carImageMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insertCarDetail(CarDetailDTO carDetailDTO){
        if (carDetailDTO == null || carDetailDTO.getCarDetailPO() == null) {
            return ;
        }
        CarDetailPO carDetailPO = carDetailDTO.getCarDetailPO();
        CarDetailPO oldDetail = carDetailMapper.selectByCarId(carDetailPO.getCarId());
        if (oldDetail == null){
            carDetailMapper.add(carDetailPO);
        }
        List<CarImagePO> imgList = carDetailDTO.getCarImagePOList();
        if (!CollectionUtils.isEmpty(imgList)){
            List<CarImagePO> oldImgList = carImageMapper.selectByCarId(carDetailPO.getCarId());
            if (CollectionUtils.isEmpty(oldImgList)){
                for (CarImagePO imagePO : imgList){
                    carImageMapper.add(imagePO);
                }
            }
        }

    }


}
