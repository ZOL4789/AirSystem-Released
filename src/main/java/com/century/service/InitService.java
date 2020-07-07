package com.century.service;

import com.century.dao.InitDAO;
import com.century.vo.City;
import com.web.cn.xml.DataSet;
import com.web.cn.xml.DomesticAirlineLocator;
import com.web.cn.xml.DomesticAirlineSoap_PortType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitService {
    @Resource
    private InitDAO initDAO;

    public City queryCityById(int id){
        return initDAO.queryCityById(id);
    }

    public int addCity(City city){
        return initDAO.addCity(city);
    }

    public int addCityBatch(List<City> cityList){
        return initDAO.addCityBatch(cityList);
    }

    public List<City> queryAllCities(){
       return initDAO.queryAllCities();
    }
}
