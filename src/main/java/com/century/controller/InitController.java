package com.century.controller;

import com.century.service.InitService;
import com.century.service.TicketService;
import com.century.vo.City;
import com.century.vo.SAD;
import com.web.cn.xml.DataSet;
import com.web.cn.xml.DomesticAirlineLocator;
import com.web.cn.xml.DomesticAirlineSoap_PortType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/init")
@SessionAttributes({"startCity", "arriveCity", "theDate"})
public class InitController {
    @Resource
    private InitService initService;

    //判断数据库中是否已经存在城市，若否，则从网上获取，并且保存到数据库中
    @PostConstruct
    public void init(){
        List<City> cityList = initService.queryAllCities();
        if(cityList.size() <= 0) {
            //服务类接口（具体不知道是啥，莽就对了）
           DomesticAirlineLocator domesticAirlineLocator = new DomesticAirlineLocator();
            try {
                DomesticAirlineSoap_PortType domesticAirline = domesticAirlineLocator.getDomesticAirlineSoap12();
                DataSet dataSet = new DataSet(domesticAirline.getDomesticCity().get_any());
                for(int i = 0; i < dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().getLength(); i++){
                    City city = new City();
                    String enCityName=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                    String cnCityName=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
                    String abbreviation=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
                    city.setEnCityName(enCityName);
                    city.setCnCityName(cnCityName);
                    city.setAbbreviation(abbreviation);
                    cityList.add(city);
                }
                //写入数据库
                initService.addCityBatch(cityList);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (javax.xml.rpc.ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    //从数据库中获取城市（出发地、目的地）
    @ResponseBody
    @RequestMapping("/getCities")
    public List<City> getCities(){
        return initService.queryAllCities();
    }

    //从Cookie中获取用户的出发地、目的地和出发日期
    @ResponseBody
    @RequestMapping("/getSAD")
    public SAD getSAD(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        SAD sad = new SAD();
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("startCity")){
                    try {
                        sad.setStartCity(URLDecoder.decode(cookie.getValue(),"utf-8"));     //中文转码，赋值
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if(cookie.getName().equals("arriveCity")){
                    try {
                        sad.setArriveCity(URLDecoder.decode(cookie.getValue(),"utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if(cookie.getName().equals("theDate")){
                    sad.setTheDate(cookie.getValue());
                }
            }
        }
        return sad;
    }

    //前台传入用户所选择的出发地、目的地和出发日期，保存到Cookie中
    @ResponseBody
    @RequestMapping(value = "/search")
    public void search(@RequestBody Map<String, Object> map, HttpServletResponse response, HttpServletRequest request){
        String startCity = (String)map.get("startCity");
        String arriveCity = (String)map.get("arriveCity");
        String theDate = (String)map.get("theDate");
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("startCity")){
                    try {
                        String startCityEncode = URLEncoder.encode(startCity,"utf-8");      //中文保存到Cookie中乱码，需要转码保存
                        Cookie cookieStartCity = new Cookie("startCity", startCityEncode);
                        cookieStartCity.setMaxAge(60 * 60 * 24 * 7);        //设置Cookie有效时间
                        cookieStartCity.setPath("/");                       //设置全局访问
                        //cookie.setHttpOnly(true);
                        response.addCookie(cookieStartCity);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if(cookie.getName().equals("arriveCity")){
                    try{
                        String arriveCityEncode = URLEncoder.encode(arriveCity, "utf-8");
                        Cookie cookieArriveCity = new Cookie("arriveCity", arriveCityEncode);
                        cookieArriveCity.setMaxAge(60 * 60 * 24 * 7);
                        cookieArriveCity.setPath("/");           //设置全局访问
                        //cookieArriveCity.setHttpOnly(true);
                        response.addCookie(cookieArriveCity);
                    }catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                }
                if(cookie.getName().equals("theDate")){
                    Cookie cookieDate = new Cookie("theDate", theDate);
                    cookieDate.setMaxAge(60 * 60 * 24 * 7);
                    cookieDate.setPath("/");           //设置全局访问
                    //cookieArriveCity.setHttpOnly(true);
                    response.addCookie(cookieDate);
                }
            }
        }

    }
}
