package com.jim.spring.boot.spider.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jim.spring.boot.spider.client.IpClient;
import com.jim.spring.boot.spider.client.IpClient2;
import com.jim.spring.boot.spider.dto.IpDTO;
import com.jim.spring.boot.spider.dto.RespDTO;
import com.jim.spring.boot.spider.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@Slf4j
public class IpUtils {


    private static List<IpDTO> ipList;


    public static List<IpDTO> get89IpList(int startPage,int endPage){
        List<IpDTO> ipList = new ArrayList<>();
        for (int i =startPage; i < endPage; i++){
            try {
                String url = "http://www.89ip.cn/index_" + i + ".html";
                Document document = Jsoup.connect(url).timeout(3000).get();
                Elements elements = document.select("tr");
                for (Element element : elements){
                    Elements elements1 = element.select("td");
                    if (elements1.size() < 5){
                        continue;
                    }
                    IpDTO ipDTO = new IpDTO();
                    ipDTO.setIp(elements1.get(0).text().trim());
                    ipDTO.setPort(Integer.parseInt(elements1.get(1).text().trim()));
                    ipDTO.setCity(elements1.get(2).text().trim());
                    ipDTO.setIsp(elements1.get(3).text().trim());
                    ipList.add(ipDTO);
                }
            } catch (Exception e) {
                log.error("获取89代理IP发生异常：{}",e.toString(),e);
            }
        }
        ipList.addAll(getWanDouIp());
        validIp(ipList);
        log.info("IP列表为：{}",JSON.toJSON(ipList));
        return ipList;

    }

    public static List<IpDTO> getWanDouIp(){
        String json = "[{\"city\":\"浙江省嘉兴市嘉善县\",\"port\":9999,\"ip\":\"125.123.139.177\",\"isp\":\"电信\"},{\"city\":\"陕西省渭南市临渭区\",\"port\":50235,\"ip\":\"124.89.33.59\",\"isp\":\"联通\"},{\"city\":\"安徽省合肥市巢湖市\",\"port\":808,\"ip\":\"117.68.195.114\",\"isp\":\"电信\"},{\"city\":\"河南省焦作市\",\"port\":9999,\"ip\":\"1.197.204.109\",\"isp\":\"电信\"},{\"city\":\"浙江省绍兴市\",\"port\":44524,\"ip\":\"115.231.5.230\",\"isp\":\"电信\"},{\"city\":\"湖北省随州市\",\"port\":9999,\"ip\":\"111.177.180.107\",\"isp\":\"电信\"},{\"city\":\"湖北省鄂州市\",\"port\":9999,\"ip\":\"58.55.231.80\",\"isp\":\"电信\"},{\"city\":\"湖北省随州市\",\"port\":9999,\"ip\":\"111.177.185.57\",\"isp\":\"电信\"},{\"city\":\"山东省济南市\",\"port\":8118,\"ip\":\"123.232.200.241\",\"isp\":\"联通\"},{\"city\":\"浙江省台州市\",\"port\":9999,\"ip\":\"183.148.138.125\",\"isp\":\"电信\"},{\"city\":\"浙江省嘉兴市嘉善县\",\"port\":9999,\"ip\":\"125.123.137.33\",\"isp\":\"电信\"},{\"city\":\"湖北省随州市\",\"port\":9999,\"ip\":\"111.177.183.219\",\"isp\":\"电信\"},{\"city\":\"广东省中山市\",\"port\":9797,\"ip\":\"14.20.235.195\",\"isp\":\"电信\"},{\"city\":\"浙江省嘉兴市嘉善县\",\"port\":9999,\"ip\":\"125.123.136.54\",\"isp\":\"电信\"},{\"city\":\"江西省宜春市\",\"port\":9999,\"ip\":\"115.151.3.183\",\"isp\":\"电信\"},{\"city\":\"河北省唐山市\",\"port\":9999,\"ip\":\"27.191.234.69\",\"isp\":\"电信\"},{\"city\":\"上海市\",\"port\":80,\"ip\":\"47.100.250.246\",\"isp\":\"阿里云\"},{\"city\":\"河南省洛阳市\",\"port\":47918,\"ip\":\"125.40.109.154\",\"isp\":\"联通\"},{\"city\":\"湖北省鄂州市\",\"port\":9999,\"ip\":\"221.235.234.58\",\"isp\":\"电信\"},{\"city\":\"湖北省鄂州市\",\"port\":9999,\"ip\":\"221.235.234.207\",\"isp\":\"电信\"},{\"city\":\"江苏省扬州市\",\"port\":9999,\"ip\":\"180.119.141.3\",\"isp\":\"电信\"},{\"city\":\"湖北省\",\"port\":9999,\"ip\":\"27.29.92.132\",\"isp\":\"电信\"},{\"city\":\"湖北省随州市\",\"port\":9999,\"ip\":\"111.177.166.84\",\"isp\":\"电信\"},{\"city\":\"湖南省岳阳市\",\"port\":9999,\"ip\":\"110.52.235.75\",\"isp\":\"联通\"},{\"city\":\"湖北省随州市\",\"port\":9999,\"ip\":\"111.177.189.253\",\"isp\":\"电信\"},{\"city\":\"浙江省嘉兴市嘉善县\",\"port\":9999,\"ip\":\"125.123.143.21\",\"isp\":\"电信\"},{\"city\":\"湖北省随州市\",\"port\":9999,\"ip\":\"111.177.177.141\",\"isp\":\"电信\"},{\"city\":\"湖南省岳阳市\",\"port\":9999,\"ip\":\"110.52.235.236\",\"isp\":\"联通\"},{\"city\":\"湖南省岳阳市\",\"port\":9999,\"ip\":\"110.52.235.29\",\"isp\":\"联通\"},{\"city\":\"湖北省咸宁市\",\"port\":9999,\"ip\":\"121.61.1.173\",\"isp\":\"电信\"},{\"city\":\"江苏省南通市\",\"port\":9999,\"ip\":\"112.85.164.120\",\"isp\":\"联通\"},{\"city\":\"浙江省嘉兴市嘉善县\",\"port\":9999,\"ip\":\"125.123.142.5\",\"isp\":\"电信\"},{\"city\":\"浙江省台州市路桥区\",\"port\":9999,\"ip\":\"125.126.211.92\",\"isp\":\"电信\"},{\"city\":\"浙江省嘉兴市南湖区\",\"port\":9999,\"ip\":\"125.123.141.50\",\"isp\":\"电信\"}]";
        List<IpDTO> list = JSON.parseObject(json,new TypeReference<List<IpDTO>>(){});
        log.info("豌豆ip列表为：{}",list);
        return list;
    }

    public static List<IpDTO> get66IpList(int startPage,int endPage){
        List<IpDTO> ipList = new ArrayList<>();
        for (int i =startPage; i < endPage; i++){
            try {
                String url = "http://www.66ip.cn/areaindex_"+ i +"/1.html";
                Document document = Jsoup.connect(url).timeout(3000).get();
                Elements elements = document.select("tr");
                for (Element element : elements){
                    Elements elements1 = element.select("td");
                    if (elements1.size() < 5){
                        continue;
                    }
                    if ("ip".equals(elements1.get(0).text())){
                        continue;
                    }
                    IpDTO ipDTO = new IpDTO();
                    ipDTO.setIp(elements1.get(0).text().trim());
                    ipDTO.setPort(Integer.parseInt(elements1.get(1).text().trim()));
                    ipDTO.setCity(elements1.get(2).text().trim());
                    ipDTO.setIsp(elements1.get(3).text().trim());
                    ipList.add(ipDTO);
                }
            } catch (Exception e) {
                log.error("获取89代理IP发生异常：{}",e.toString(),e);
            }
        }
        ipList.addAll(getWanDouIp());
        validIp(ipList);
        log.info("IP列表为：{}",JSON.toJSON(ipList));
        return ipList;

    }

    /**
     * 获取极光代理IP
     * @return 代理IP
     */
    public static IpDTO getIp(){
        if (CollectionUtils.isEmpty(ipList)){
            IpClient ipClient = SpringBeanUtil.getBean(IpClient.class);
            RespDTO<List<IpDTO>> respDTO = ipClient.getIpList();
            log.info("获取极光IP代理信息为：{}",respDTO);
            if (!respDTO.getSuccess()){
                throw new ServiceException("获取IP发生异常！");
            }
            List<IpDTO> ipDTOList = respDTO.getData();
            IpUtils.validIp(ipDTOList);
            ipList = ipDTOList;
        }
        IpDTO ipDTO = ipList.get(new Random().nextInt(ipList.size()));
        if (LocalDateTime.now().isAfter(ipDTO.getExpire_time())){
            log.info("IP信息：{}，已经过期，移除", ipDTO);
            getIp();
            ipList.remove(ipDTO);
        }
        log.info("获取IP信息为：{}", ipDTO);
        return ipDTO;
    }

    public static synchronized IpDTO getMiPuIp(){
        if (CollectionUtils.isEmpty(ipList)){
            List<IpDTO> ipDTOList = new ArrayList<>();
            IpClient2 ipClient = SpringBeanUtil.getBean(IpClient2.class);
            JSONObject jsonObject = ipClient.getIpList();
            if (0 == (Integer) jsonObject.get("code")){
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
            IpUtils.validIp(ipDTOList);
            ipList = ipDTOList;
        }
        IpDTO ipDTO = ipList.get(new Random().nextInt(ipList.size()));
//        if (LocalDateTime.now().isAfter(ipDTO.getExpire_time())){
//            log.info("IP信息：{}，已经过期，移除", ipDTO);
//            getIp();
//            ipList.remove(ipDTO);
//        }
        log.info("获取IP信息为：{}", ipDTO);
        return ipDTO;
    }


    public static void validIp(List<IpDTO> ipList){
        Iterator<IpDTO> iterator = ipList.iterator();
        int i = 0;
        while (iterator.hasNext()){
            IpDTO ipDTO = iterator.next();
            log.info("开始IP验证，IP信息：{}",ipDTO);
            try {
//                String url = "http://httpbin.org/get";
                i++;
                String url = "https://www.baidu.com";
                Jsoup.connect(url).timeout(2000).proxy(ipDTO.getIp(),ipDTO.getPort()).get();
                log.info("IP验证成功，IP信息：{}",ipDTO);
            } catch (Exception e) {
                log.error("代理IP：{}，第{}个，校验89代理IP失败：{}",ipDTO,i,e.toString());
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws Exception{
//        List<IpDTO> ipList = IpUtils.get89IpList(1,2);
//        List<IpDTO> ipList = IpUtils.get66IpList(1,10);
//
//        log.info("ipList大小：{}，列表为：{}",ipList.size(),ipList);
//        String ip = "27.112.70.141";
//        int port = 50431;
        String ip = "180.114.169.10";
        int port = 25342;
        String url = "http://www.baidu.com";
//        String url = "http://www.daxiangdaili.com/";
        Jsoup.connect(url).timeout(30000).proxy(ip,port).get();
        log.debug("IP验证成功，IP信息：{}",ip+port);
    }
}
