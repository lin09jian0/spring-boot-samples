package com.jim.spring.boot.spider.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jim.spring.boot.spider.client.IpClient2;
import com.jim.spring.boot.spider.dto.IpDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jim lin
 * @date 2019/2/26.
 */
@Service
public class IP {

    @Resource
    private IpClient2 ipClient2;

    public void getIp(){
        List<IpDTO> ipDTOList = new ArrayList<>();
        JSONObject jsonObject = ipClient2.getIpList();
        if (0 == (int)jsonObject.get("code")){
            JSONArray jsonArray =jsonObject.getJSONArray("result");
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()){
                JSONObject jsonObject1 = (JSONObject) iterator.next();
                String ipAndPort = (String)jsonObject1.get("ip:port");
                String[] ips = ipAndPort.split(":");
                IpDTO ipDTO = new IpDTO();
                ipDTO.setIp(ips[0]);
                ipDTO.setPort(Integer.parseInt(ips[1]));
                ipDTOList.add(ipDTO);
            }
//            for (JSONObject jsonObject1 : (JSONObject[])jsonArray.toArray()){
//            }
        }
        jsonObject.get("result");
    }
}
