package com.century.dao;

import com.century.vo.SAD;
import com.century.vo.Ticket;

import java.util.List;

public interface TicketDAO {
    Ticket queryAllTickets();
    Ticket queryTicketById(int Id);
    int addTicketBatch(List<Ticket> ticketList);
    int addTicket(Ticket ticket);
    List<String> queryAllAirCode();
    List<Ticket> queryTicketBySAD(SAD sad);
    int queryIdByAirCode(String airCode);
    Ticket queryTicketByAirCode(String airCode);
}
