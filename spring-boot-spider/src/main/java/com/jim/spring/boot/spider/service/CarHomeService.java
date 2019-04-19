package com.jim.spring.boot.spider.service;

import com.alibaba.fastjson.JSON;
import com.jim.spring.boot.spider.dao.CarDetailDAO;
import com.jim.spring.boot.spider.dto.CarDetailDTO;
import com.jim.spring.boot.spider.dto.CarInfoDTO;
import com.jim.spring.boot.spider.enums.ErrorCodeEnum;
import com.jim.spring.boot.spider.exception.ServiceException;
import com.jim.spring.boot.spider.mapper.CarCityMapper;
import com.jim.spring.boot.spider.mapper.CarDetailMapper;
import com.jim.spring.boot.spider.mapper.CarInfoMapper;
import com.jim.spring.boot.spider.po.CarCityPO;
import com.jim.spring.boot.spider.po.CarDetailPO;
import com.jim.spring.boot.spider.po.CarInfoPO;
import com.jim.spring.boot.spider.utils.HtmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jim lin
 * @date 2019/2/20.
 */
@Service
@Slf4j
public class CarHomeService {

    @Autowired
    private HtmlParser htmlParser;

    @Resource
    private CarInfoMapper carInfoMapper;

    @Resource
    private CarCityMapper carCityMapper;
    @Resource
    private CarDetailDAO carDetailDAO;

    @Resource
    private CarDetailMapper carDetailMapper;

    @Resource
    private ThreadPoolTaskExecutor executor;

    public void parseCarInfo(){
        List<CarCityPO> cityList = getList();
        if (CollectionUtils.isEmpty(cityList)){
            return;
        }
        int divisor = 15;
        int mode = cityList.size() / divisor;
        for (int i = 0;i < mode; i++){
            List<CarCityPO> tempList = cityList.subList(i*divisor, (i+1)*divisor);
            log.info("开始对城市：{}，进行处理",JSON.toJSON(tempList));
//            new CarRunnable(tempList).run();
            new Thread(new CarRunnable(tempList)).start();
        }
//        for (CarCityPO cityPO : cityList){
////            new Thread(new CarRunnable(cityPO)).start();
//            new CarRunnable(cityPO).run();
//        }

    }

    private List<CarCityPO> getList(){
        List<CarCityPO> cityList = carCityMapper.list();
        if (CollectionUtils.isEmpty(cityList)){
            throw new ServiceException(ErrorCodeEnum.CITY_INFO_NOT_EXIST);
        }
        return cityList;
    }

    public void parseDetail(Integer startNum,Integer endNum){
        Integer count = carInfoMapper.selectCount();
        if (count == null){
            return;
        }
        if (count > endNum){
            count = endNum;
        }
        int divisor = 1000;
        int mode = count / divisor;
        int start = startNum / divisor;
        for (int i = start;i < mode; i++){
            List<CarInfoPO> carInfoPOList = carInfoMapper.selectByPage(i*divisor, divisor);
//            log.info("车辆信息：{}，进行处理",JSON.toJSON(carInfoPOList));
            if (CollectionUtils.isEmpty(carInfoPOList)){
                continue;
            }
//            executor.execute(new CarDetailRunnable(carInfoPOList));
            new Thread(new CarDetailRunnable(carInfoPOList)).start();
        }

    }
}
