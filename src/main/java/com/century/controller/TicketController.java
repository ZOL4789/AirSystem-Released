package com.century.controller;

import com.century.service.TicketService;
import com.century.vo.SAD;
import com.century.vo.Ticket;
import com.web.cn.xml.DataSet;
import com.web.cn.xml.DomesticAirlineLocator;
import com.web.cn.xml.DomesticAirlineSoap_PortType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Resource
    private TicketService ticketService;
    @ResponseBody
    @RequestMapping("/getTickets")
    public List<Ticket> getTickets(HttpServletRequest request){
        String startCity = "";
        String arriveCity = "";
        String theDate = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("startCity")){
                    try {
                        startCity = URLDecoder.decode(cookie.getValue(),"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if(cookie.getName().equals("arriveCity")){
                    try {
                        arriveCity =  URLDecoder.decode(cookie.getValue(),"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if(cookie.getName().equals("theDate")){
                    theDate = cookie.getValue();
                }
            }
        }
        System.out.println("theDate = " + theDate + startCity + arriveCity);
        List<Ticket> ticketList = new ArrayList<Ticket>();
        SAD sad = new SAD();
        sad.setStartCity(startCity.trim());
        sad.setArriveCity(arriveCity.trim());
        sad.setTheDate(theDate.trim());
        ticketList = ticketService.queryTicketBySAD(sad);
        if(ticketList.size() == 0){
            DomesticAirlineLocator domesticAirlineLocator = new DomesticAirlineLocator();
            try {
                DomesticAirlineSoap_PortType domesticAirline = domesticAirlineLocator.getDomesticAirlineSoap12();
                DataSet dataSet = new DataSet(domesticAirline.getDomesticAirlinesTime(startCity, arriveCity, theDate, "").get_any());
                int num = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().getLength();
                if (num > 0 && !dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getNodeValue().equals("没有航班")) {
                    for (int i = 0; i < num; i++) {
                        Ticket ticket = new Ticket();
                        String airCode = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
                        String company = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                        String startDrome = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
                        String arriveDrome = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(3).getChildNodes().item(0).getNodeValue();
                        String startTime = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(4).getChildNodes().item(0).getNodeValue();
                        String arriveTime = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(5).getChildNodes().item(0).getNodeValue();
                        String mode = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(6).getChildNodes().item(0).getNodeValue();
                        String airStop = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(7).getChildNodes().item(0).getNodeValue();
                        String week = dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(8).getChildNodes().item(0).getNodeValue();
                        ticket.setAirCode(airCode);
                        ticket.setCompany(company);
                        ticket.setStartDrome(startDrome);
                        ticket.setArriveDrome(arriveDrome);
                        ticket.setStartTime(startTime);
                        ticket.setArriveTime(arriveTime);
                        ticket.setMode(mode);
                        ticket.setAirStop(airStop);
                        ticket.setWeek(week);
                        ticket.setDate(theDate);
                        ticketList.add(ticket);
                    }
                    ticketService.addTicketBatch(ticketList);
                }
            } catch(RemoteException e){
                e.printStackTrace();
            } catch(javax.xml.rpc.ServiceException e) {
                e.printStackTrace();
            }
        }
        return ticketList;
    }
}
