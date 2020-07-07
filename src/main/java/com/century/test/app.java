package com.century.test;

import com.google.protobuf.ServiceException;
import com.web.cn.xml.DataSet;
import com.web.cn.xml.DomesticAirlineLocator;
import com.web.cn.xml.DomesticAirlineSoap_PortType;

import java.rmi.RemoteException;

public class app {
    public static void main(String[] args){
        DomesticAirlineLocator domesticAirlineLocator = new DomesticAirlineLocator();
        try {
            DomesticAirlineSoap_PortType domesticAirline = domesticAirlineLocator.getDomesticAirlineSoap12();
            DataSet dataSet = new DataSet(domesticAirline.getDomesticAirlinesTime("北京","上海","2020-7-20","").get_any());
            System.out.println(dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().getLength());
            for(int i = 0; i < dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().getLength(); i++){
                for(int j = 0; j < dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().getLength(); j++){
                    System.out.println(dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(j).getChildNodes().item(0).getNodeValue());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (javax.xml.rpc.ServiceException e) {
            e.printStackTrace();
        }
    }
}
