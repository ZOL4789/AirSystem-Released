package com.century.controller;

import com.century.service.TicketService;
import com.century.vo.Ticket;
import com.web.cn.xml.DataSet;
import com.web.cn.xml.DomesticAirlineLocator;
import com.web.cn.xml.DomesticAirlineSoap_PortType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public List<Ticket> getTickets(HttpSession session){
        String startCity = (String)session.getAttribute("startCity");
        String arriveCity = (String)session.getAttribute("arriveCity");
        String theDate = (String)session.getAttribute("theDate");
        List<Ticket> ticketList = new ArrayList<Ticket>();
        System.out.println("___________________________________getTickets----------------------------------------------");
       DomesticAirlineLocator domesticAirlineLocator = new DomesticAirlineLocator();
        try {
            DomesticAirlineSoap_PortType domesticAirline = domesticAirlineLocator.getDomesticAirlineSoap12();
            DataSet dataSet = new DataSet(domesticAirline.getDomesticAirlinesTime(startCity, arriveCity, theDate, "").get_any());
            for(int i = 0; i < dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().getLength(); i++){
              //  System.out.println(dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().getLength());
                Ticket ticket = new Ticket();
                String airCode=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                String company=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
                String startDrome=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
                String arriveDrome=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(3).getChildNodes().item(0).getNodeValue();
                String startTime=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(4).getChildNodes().item(0).getNodeValue();
                String arriveTime=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(5).getChildNodes().item(0).getNodeValue();
                String mode=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(6).getChildNodes().item(0).getNodeValue();
                String airStop=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(7).getChildNodes().item(0).getNodeValue();
                String week=dataSet.get_any()[1].getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(8).getChildNodes().item(0).getNodeValue();
                ticket.setAirCode(airCode);
                ticket.setCompany(company);
                ticket.setAirStartDrome(startDrome);
                ticket.setArriveDrome(arriveDrome);
                ticket.setStartTime(startTime);
                ticket.setArriveTime(arriveTime);
                ticket.setMode(mode);
                ticket.setAirStop(airStop);
                ticket.setWeek(week);
                ticketList.add(ticket);
            }
            //ticketService.addTicketBatch(ticketList);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (javax.xml.rpc.ServiceException e) {
            e.printStackTrace();
        }
        return ticketList;
    }
}
