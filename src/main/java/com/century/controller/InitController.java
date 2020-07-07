package com.century.controller;

import com.century.service.InitService;
import com.century.vo.City;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.web.cn.xml.DataSet;
import com.web.cn.xml.DomesticAirlineLocator;
import com.web.cn.xml.DomesticAirlineSoap_PortType;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @PostConstruct
    public void init(){
        List<City> cityList = new ArrayList<City>();
        System.out.println("_____________________________________________fasdf----------------------------------------------");
//       DomesticAirlineLocator domesticAirlineLocator = new DomesticAirlineLocator();
//        try {
//            DomesticAirlineSoap_PortType domesticAirline = domesticAirlineLocator.getDomesticAirlineSoap12();
//            DataSet dataSet = new DataSet(domesticAirline.getDomesticCity().get_any());
//            for(int i = 0; i < dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().getLength(); i++){
//                City city = new City();
//                String enCityName=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
//                String cnCityName=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
//                String abbreviation=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
//                city.setEnCityName(enCityName);
//                city.setCnCityName(cnCityName);
//                city.setAbbreviation(abbreviation);
//                cityList.add(city);
//            }
//            initService.addCityBatch(cityList);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (javax.xml.rpc.ServiceException e) {
//            e.printStackTrace();
//        }
    }

    @ResponseBody
    @RequestMapping("/getCities")
    public List<City> getCities(){
        return initService.queryAllCities();
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public void search(@RequestBody Map<String, Object> map, HttpSession session){
        String startCity = (String)map.get("startCity");
        String arriveCity = (String)map.get("arriveCity");
        String theDate = (String)map.get("theDate");

        session.setAttribute("startCity", startCity);
        session.setAttribute("arriveCity", arriveCity);
        session.setAttribute("theDate", theDate);
    }

    @ResponseBody
    @RequestMapping("/getTest")
    public void getTest(HttpSession session){
        System.out.println("UserName getTest = " + session.getAttribute("userName"));
    }

    @ResponseBody
    @RequestMapping("/setTest")
    public void setTest(HttpSession session){
        session.setAttribute("userName", "Air321");
        System.out.println("UserName setTest = " + session.getAttribute("userName"));
    }
}
