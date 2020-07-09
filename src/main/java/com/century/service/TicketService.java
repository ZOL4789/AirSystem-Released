package com.century.service;

import com.century.dao.TicketDAO;
import com.century.vo.SAD;
import com.century.vo.Ticket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {
    @Resource
    private TicketDAO ticketDAO;

    public Ticket queryTicketById(int id){
        return ticketDAO.queryTicketById(id);
    }

    public int addTicketBatch(List<Ticket> ticketList){
        return ticketDAO.addTicketBatch(ticketList);
    }

    public int addTicket(Ticket ticket){
        String airCode = ticket.getAirCode();
        List<String> airCodeList = ticketDAO.queryAllAirCode();
        if(airCodeList.contains(airCode)){
            return -1;
        }
        return ticketDAO.addTicket(ticket);
    }

    public List<Ticket> queryTicketBySAD(SAD sad){
        return ticketDAO.queryTicketBySAD(sad);
    }

    public int queryIdByAirCode(String airCode){return ticketDAO.queryIdByAirCode(airCode);}

    public Ticket queryTicketByAirCode(String airCode){return ticketDAO.queryTicketByAirCode(airCode);}

    public Ticket queryTicketByMapCSA(Map<String,Object> map){return ticketDAO.queryTicketByMapCSA(map);}
}
