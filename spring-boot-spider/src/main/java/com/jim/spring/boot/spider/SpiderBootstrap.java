package com.jim.spring.boot.spider;

import com.jim.spring.boot.spider.po.CarInfoPO;
import com.jim.spring.boot.spider.service.CarHomeService;
import com.jim.spring.boot.spider.utils.HtmlParser;
import com.jim.spring.boot.spider.utils.IP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@SpringBootApplication
@MapperScan("com.jim.spring.boot.spider.mapper")
@EnableFeignClients
public class SpiderBootstrap {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpiderBootstrap.class,args);
        CarInfoPO carInfoPO  = new CarInfoPO();
        carInfoPO.setId(5);
        carInfoPO.setDealerId("203940");
        carInfoPO.setCarInfoId("28822514");
//        HtmlParser carHomeService = applicationContext.getBean(HtmlParser.class);
//        try {
//            carHomeService.parseCarDetailInfo(carInfoPO);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
