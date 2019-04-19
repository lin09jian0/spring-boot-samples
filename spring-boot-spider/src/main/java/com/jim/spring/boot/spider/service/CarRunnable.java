package com.jim.spring.boot.spider.service;

import com.jim.spring.boot.spider.dto.CarInfoDTO;
import com.jim.spring.boot.spider.mapper.CarInfoMapper;
import com.jim.spring.boot.spider.po.CarCityPO;
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
public class CarRunnable implements Runnable{

    private List<CarCityPO> cityPOList;

    public CarRunnable(List<CarCityPO>  cityPOList){
        this.cityPOList = cityPOList;
    }

    @Override
    public void run() {
        CarInfoMapper carInfoMapper = SpringBeanUtil.getBean(CarInfoMapper.class);
        HtmlParser htmlParser = SpringBeanUtil.getBean(HtmlParser.class);
        List<String> regionList = getRegionList();
        for (CarCityPO cityPO : cityPOList) {
            if ("0".equals(cityPO.getParentCode())){
                continue;
            }
            for (String region : regionList) {
                int totalCount = 2, count = 1;
                while (count < totalCount) {
                    try {
                        Thread.sleep(300L);
                        String href = "https://www.che168.com/" + cityPO.getNameSpelling() + "/" + region + "/" + "a0_0msdgscncgpi1ltocsp" + count + "exx0/";
                        log.info("开始进行解析：{}", href);
                        CarInfoDTO carInfoDTO = htmlParser.parseOldCar(href);
                        log.info("解析结果为：{}", carInfoDTO);
                        batchInsert(carInfoDTO.getList(), carInfoMapper);
                        count++;
                        totalCount = carInfoDTO.getTotalPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void batchInsert(List<CarInfoPO> carInfoPOList, CarInfoMapper carInfoMapper){
        if (CollectionUtils.isEmpty(carInfoPOList)){
            log.error("未查询到汽车数据！");
            return ;
        }
        List<CarInfoPO> insertList = new ArrayList<>();
        for (CarInfoPO carInfoPO : carInfoPOList){
            try {
                CarInfoPO oldCarInfo = carInfoMapper.getByCarInfoId(carInfoPO.getCarInfoId());
                if (oldCarInfo == null){
                    insertList.add(carInfoPO);
                }
                if (oldCarInfo  != null && StringUtils.isEmpty(oldCarInfo.getImgUrl()) ){
                    log.info("更新数据：{}",carInfoPO);
                    carInfoMapper.updateByInfoId(carInfoPO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("需要新增的数据大小为：{}，数据为：{}",insertList.size(),insertList);
        if (!CollectionUtils.isEmpty(insertList)){
            carInfoMapper.batchAdd(insertList);
        }
    }


    private List<String> getRegionList2(){
        List<String> regionList = new ArrayList<>();
        regionList.add("0_3");
        regionList.add("3_5");
        regionList.add("5_8");
        regionList.add("8_10");
        regionList.add("10_15");
        regionList.add("15_20");
        regionList.add("20_30");
        regionList.add("30_50");
        regionList.add("50-0");
        return regionList;
    }

    private List<String> getRegionList(){
        List<String> regionList = new ArrayList<>();
        regionList.add("50-0");
        regionList.add("30_50");
        regionList.add("20_30");
        regionList.add("15_20");
        regionList.add("10_15");
        regionList.add("8_10");
        regionList.add("5_8");
        regionList.add("3_5");
        regionList.add("0_3");
        return regionList;
    }

}
