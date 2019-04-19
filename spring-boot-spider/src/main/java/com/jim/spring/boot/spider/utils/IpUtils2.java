package com.jim.spring.boot.spider.utils;

import com.jim.spring.boot.spider.client.IpClient;
import com.jim.spring.boot.spider.dto.IpDTO;
import com.jim.spring.boot.spider.dto.RespDTO;
import com.jim.spring.boot.spider.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@Slf4j
public class IpUtils2 {

    private static List<IpDTO> ipList;

    /**
     * 获取极光代理IP
     * @return 代理IP
     */
    public static synchronized IpDTO getIp(){
        if (CollectionUtils.isEmpty(ipList)){
            IpClient ipClient = SpringBeanUtil.getBean(IpClient.class);
            RespDTO<List<IpDTO>> respDTO = ipClient.getPandaIpList();
//            log.info("获取极光IP代理信息为：{}",respDTO);
            if (0 != (respDTO.getCode())){
                throw new ServiceException("获取IP发生异常！");
            }
            List<IpDTO> ipDTOList = respDTO.getObj();
            IpUtils.validIp(ipDTOList);
            ipList = ipDTOList;
        }
        IpDTO ipDTO = ipList.get(new Random().nextInt(ipList.size()));
//        if (LocalDateTime.now().isAfter(ipDTO.getExpire_time())){
//            log.info("IP信息：{}，已经过期，移除", ipDTO);
//            getIp();
//            ipList.remove(ipDTO);
//        }
//        log.info("获取IP信息为：{}", ipDTO);
        return ipDTO;
    }

    public static synchronized void remove(IpDTO ipDTO){
        ipList.remove(ipDTO);
    }

}
