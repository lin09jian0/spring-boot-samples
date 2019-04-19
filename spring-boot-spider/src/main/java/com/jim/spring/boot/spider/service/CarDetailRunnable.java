package com.jim.spring.boot.spider.service;

import com.jim.spring.boot.spider.dao.CarDetailDAO;
import com.jim.spring.boot.spider.dto.CarDetailDTO;
import com.jim.spring.boot.spider.dto.CarInfoDTO;
import com.jim.spring.boot.spider.mapper.CarDetailMapper;
import com.jim.spring.boot.spider.mapper.CarInfoMapper;
import com.jim.spring.boot.spider.po.CarCityPO;
import com.jim.spring.boot.spider.po.CarDetailPO;
import com.jim.spring.boot.spider.po.CarInfoPO;
import com.jim.spring.boot.spider.utils.HtmlParser;
import com.jim.spring.boot.spider.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jim lin
 * @date 2019/2/21.
 */
@Slf4j
public class CarDetailRunnable implements Runnable{

    private List<CarInfoPO> carInfoPOList;

    public CarDetailRunnable(List<CarInfoPO> carInfoPOList){
        this.carInfoPOList = carInfoPOList;
    }

    @Override
    public void run() {
        CarDetailDAO carDetailDAO = SpringBeanUtil.getBean(CarDetailDAO.class);
        HtmlParser htmlParser = SpringBeanUtil.getBean(HtmlParser.class);
        CarDetailMapper carDetailMapper = SpringBeanUtil.getBean(CarDetailMapper.class);
        for (int i =0;i < carInfoPOList.size();){
            CarInfoPO carInfoPO = carInfoPOList.get(i);
//        for (CarInfoPO carInfoPO : carInfoPOList){
            try {
//                log.info("当前线程为：{}",Thread.currentThread());
                CarDetailPO oldDetail = carDetailMapper.selectByCarId(carInfoPO.getId());
                if (oldDetail != null){
                    continue;
                }
//                Thread.sleep(300);
                CarDetailDTO detailDTO = htmlParser.parseCarDetailInfo(carInfoPO);
                if (detailDTO == null ){
                    i++;
                    continue;
                }
//                log.info("新增数据为：{}",detailDTO);
                carDetailDAO.insertCarDetail(detailDTO);
                i++;
            } catch (Exception e) {
                log.error("获取详情发生异常！：{}",e.toString());
            }
        }
    }

}
