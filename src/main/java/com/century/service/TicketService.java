package com.century.service;

import com.century.dao.TicketDAO;
import com.century.vo.Ticket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TicketService {
    @Resource
    private TicketDAO ticketDAO;

    public Ticket queryTicketByAirCode(String airCode){
        return ticketDAO.queryTicketByAirCode(airCode);
    }
}
