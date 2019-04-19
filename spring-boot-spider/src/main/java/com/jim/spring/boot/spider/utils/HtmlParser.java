package com.jim.spring.boot.spider.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jim.spring.boot.spider.client.IpClient;
import com.jim.spring.boot.spider.client.IpClient2;
import com.jim.spring.boot.spider.client.IpClient3;
import com.jim.spring.boot.spider.dto.CarDetailDTO;
import com.jim.spring.boot.spider.dto.CarInfoDTO;
import com.jim.spring.boot.spider.dto.IpDTO;
import com.jim.spring.boot.spider.dto.RespDTO;
import com.jim.spring.boot.spider.exception.ServiceException;
import com.jim.spring.boot.spider.mapper.CarInfoMapper;
import com.jim.spring.boot.spider.po.CarDetailPO;
import com.jim.spring.boot.spider.po.CarImagePO;
import com.jim.spring.boot.spider.po.CarInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jim lin
 * @date 2019/1/21.
 */
@Service
@Slf4j
public class HtmlParser {
    @Resource
    private IpClient ipClient;
    @Resource
    private IpClient2 ipClient2;
    @Resource
    private IpClient3 ipClient3;
    @Resource
    private CarInfoMapper carInfoMapper;

    private List<IpDTO> ipList;

    private ConcurrentHashMap<String,Integer> errorIpMap = new ConcurrentHashMap<>();

    public CarInfoDTO parseOldCar(String url) throws Exception{
        IpDTO ipDTO = null;
        try {
//                String url = "http://httpbin.org/get";
//            String url = "https://www.che168.com/shanghai/3_5/a0_0msdgscncgpi1ltocsp1ex/";
//            ipDTO =  get89Ip();
//            ipDTO =  IpUtils.getIp();

            ipDTO =  getWuYouIp();
            Document document = Jsoup.connect(url)
                    .headers(getHeaders())
                    .proxy(ipDTO.getIp(),ipDTO.getPort())
                    .timeout(10000)
                    .get();
            Elements elements = document.select("li.list-photo-li");
            Integer totalPage = Integer.parseInt(document.select("input#TotalPage").attr("value"));
            List<CarInfoPO> list = new ArrayList<>(elements.size());
            for (Element element : elements){
                if (!element.hasAttr("infoid")){
                    log.debug("筛选的元素：{}，没有infoID",element.toString());
                    continue;
                }
                CarInfoPO carInfo = buildCarInfo(element);
                list.add(carInfo);
            }
            log.info("生成汽车信息为：{}",list);
            CarInfoDTO carInfoDTO = new CarInfoDTO();
            carInfoDTO.setList(list);
            carInfoDTO.setTotalPage(totalPage);
            return carInfoDTO;
        } catch (Exception e) {
            log.error("解析二手车信息发生异常:{}",e.toString(),e);
            if(ipDTO != null){
                Integer errorCount = errorIpMap.get(ipDTO.getIp());
                if (errorCount == null){
                    errorCount = 1;
                    errorIpMap.put(ipDTO.getIp(),errorCount);
                    throw e;
                }
                if (errorCount > 3){
                    ipList.remove(ipDTO);
                }else{
                    errorIpMap.put(ipDTO.getIp(),++errorCount);
                }
            }
            throw e;
        }

    }

    public CarDetailDTO parseCarDetailInfo(CarInfoPO carInfoPO) throws Exception{
        IpDTO ipDTO = null;
        CarDetailDTO carDetailDTO = new CarDetailDTO();
        try {
            ipDTO =  IpUtils2.getIp();
//            if ("0".equals(carInfoPO.getCarInfoId())){
//                return null;
//            }
            String url = "https://www.che168.com/dealer/" + carInfoPO.getDealerId() + "/" + carInfoPO.getCarInfoId() + ".html";
            log.info("IP信息：{}，开始解析：{},url为：{}",ipDTO,carInfoPO,url);
            Document document = Jsoup.connect(url)
                    .headers(getHeaders())
                    .proxy(ipDTO.getIp(),ipDTO.getPort())
                    .timeout(8000)
                    .get();
            if (document.select("span.ic_wrong").next().html().contains("非常抱歉") == true){
                carInfoMapper.soldOut(true,carInfoPO.getId());
                return null;
            }
            Elements elements = document.select("li.grid-6");
            if (elements.size() != 12){
                return null;
            }
            CarDetailPO carDetailPO = new CarDetailPO();
            for (int i = 0;i< elements.size();i++){
                Element element = elements.get(i);
                if (i == 0){
                    carDetailPO.setAnnualSurveyDate(element.childNode(1).toString().trim());
                }
                if (i == 1){
                    carDetailPO.setInsuranceDate(element.childNode(1).toString().trim());
                }
                if (i == 2){
                    carDetailPO.setEmissionStandard(element.childNode(1).toString().trim());
                }
                if (i == 3){
                    carDetailPO.setTransferCount(element.child(1).html().trim());
                }
                if (i == 4){
                    carDetailPO.setUse(element.childNode(1).toString().trim());
                }
                if (i == 5){
                    carDetailPO.setMaintain(element.childNode(1).toString().trim());
                }
                if (i == 6){
                    carDetailPO.setEngine(element.childNode(1).toString().trim());
                }
                if (i == 7){
                    carDetailPO.setTransmission(element.childNode(1).toString().trim());
                }
                if (i == 8){
                    carDetailPO.setVehicleLevel(element.childNode(1).toString().trim());
                }
                if (i == 9){
                    carDetailPO.setColor(element.childNode(1).toString().trim());
                }
                if (i == 10){
                    carDetailPO.setFuelLabel(element.childNode(1).toString().trim());
                }
                if (i == 11){
                    carDetailPO.setDriveMode(element.childNode(1).toString().trim());
                }
            }

            Elements elements1 = document.select("li.grid-20");
            if (elements1.size() > 0){
                Element element = elements1.get(0);
                carDetailPO.setDealerName(element.childNode(1).toString().trim());
                Elements e2s = element.select("#remark_full").select(".tip-content.fn-clear");
                String desc = e2s.html();
                if (desc.indexOf("<a") >0 ){
                    desc = desc.substring(0,desc.indexOf("<a"));
                }
                carDetailPO.setCarDesc(desc);
            }
            Elements elements2 = document.select("li.grid-8");
            if (elements2.size() > 0){
                String guarantee = elements2.get(0).childNode(1).toString().trim();
                if (guarantee.contains("&nbsp")){
                    guarantee = guarantee.substring(0,guarantee.indexOf("&nbsp"));
                }
                carDetailPO.setGuaranteeExpires(guarantee);
            }
            carDetailPO.setCarId(carInfoPO.getId());
            carDetailPO.setCarInfoId(carInfoPO.getCarInfoId());
//            log.info("生成汽车信息为：{}",carDetailPO);
            carDetailDTO.setCarDetailPO(carDetailPO);
            Elements imgElements = document.select("img.imageclick");
            List<CarImagePO> imgList = new ArrayList<>(imgElements.size());
            for (Element element : imgElements){
                if (element.hasAttr("src2")){
                    CarImagePO carImagePO = new CarImagePO();
                    carImagePO.setCarId(carInfoPO.getId());
                    carImagePO.setCarInfoId(carInfoPO.getCarInfoId());
                    carImagePO.setImageUrl(element.attr("src2"));
                    imgList.add(carImagePO);
                }
            }
            carDetailDTO.setCarImagePOList(imgList);
            return carDetailDTO;
        } catch (Exception e) {
            log.error("IP:{},解析二手车信息发生异常:{}",ipDTO,e.toString());
            if(ipDTO != null){
                Integer errorCount = errorIpMap.get(ipDTO.getIp());
                if (errorCount == null){
                    errorCount = 1;
                    errorIpMap.put(ipDTO.getIp(),errorCount);
                    throw e;
//                    return null;
                }
                if (errorCount > 20){
                    IpUtils2.remove(ipDTO);
                }else{
                    errorIpMap.put(ipDTO.getIp(),++errorCount);
                }
            }
            throw e;
//            return null;
        }

    }

    public static void main(String[] args) throws Exception{
        HtmlParser htmlParser = new HtmlParser();
        CarInfoPO carInfoPO  = new CarInfoPO();
        carInfoPO.setId(5);
        carInfoPO.setDealerId("203940");
        carInfoPO.setCarInfoId("28822514");
        htmlParser.parseCarDetailInfo(carInfoPO);
    }

    private CarInfoPO buildCarInfo(Element element){
        CarInfoPO carInfo = new CarInfoPO();
        carInfo.setCarName(element.attr("carname"));
        carInfo.setCarPrice(new BigDecimal(element.attr("price")));
        carInfo.setMilleage(Double.parseDouble(element.attr("milage")));
        carInfo.setRegisterDate(element.attr("regdate") );
        String publicDate = element.attr("publicdate").replace("T", " ").replace("Z", " ");
        carInfo.setPublicDate(LocalDateTime.parse(publicDate.trim(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        carInfo.setCarInfoId(element.attr("infoid"));
        carInfo.setDealerId(element.attr("dealerid"));
        carInfo.setBranchId(element.attr("brandid"));
        carInfo.setSeriesId(element.attr("seriesid"));
        carInfo.setCityCode(element.attr("cid"));
        carInfo.setCityName(element.attr("name"));
        carInfo.setParentCityCode(element.attr("pid"));
        carInfo.setModelId(element.attr("specid"));
        carInfo.setIsNewCar(Integer.parseInt(element.attr("isnewcar")));
        carInfo.setIsExtendRepair(Integer.parseInt(element.attr("isextendrepair")));
        carInfo.setSource("01");
        carInfo.setCreateAt(new Date());
        carInfo.setImgUrl(element.select("img").first().attr("src"));
        return carInfo;
    }

    private synchronized IpDTO get89Ip(){
        log.info("获取IP，当前对象为：{}，",this);
        if (CollectionUtils.isEmpty(ipList) || ipList.size() == 0){
            ipList = IpUtils.get89IpList(1,10);
            ipList.addAll(IpUtils.get66IpList(1,10));
        }
        IpDTO ipDTO = ipList.get(new Random().nextInt(ipList.size()));
        log.info("获取IP信息为：{}", ipDTO);
        return ipDTO;
    }

    /**
     * 获取极光代理IP
     * @return 代理IP
     */
    private synchronized IpDTO getIp(){
        if (CollectionUtils.isEmpty(ipList)){
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

    public synchronized IpDTO getMiPuIp(){
        if (CollectionUtils.isEmpty(ipList)){
            List<IpDTO> ipDTOList = new ArrayList<>();
            JSONObject jsonObject = ipClient2.getIpList();
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
                    ipDTO.setHttpType((String)jsonObject1.get("http_type"));
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


    public synchronized IpDTO getWuYouIp(){
        if (CollectionUtils.isEmpty(ipList)){
            List<IpDTO> ipDTOList = new ArrayList<>();
            JSONObject jsonObject = ipClient3.getIpList();
            if (0 == (Integer) jsonObject.get("Code")){
                JSONArray jsonArray =jsonObject.getJSONArray("Data");
                Iterator iterator = jsonArray.iterator();
                while (iterator.hasNext()){
                    JSONObject jsonObject1 = (JSONObject) iterator.next();
                    IpDTO ipDTO = new IpDTO();
                    ipDTO.setIp((String)jsonObject1.get("Ip"));
                    ipDTO.setPort((Integer) jsonObject1.get("Port"));
                    ipDTOList.add(ipDTO);
                }
                //            for (JSONObject jsonObject1 : (JSONObject[])jsonArray.toArray()){
                //            }
            }else{
                return get89Ip();
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

    private Map<String,String> getHeaders(){
        Map<String,String> headers = new HashMap<>(16);
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");
        return headers;
    }

}
