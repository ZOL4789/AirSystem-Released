package com.century.dao;

import com.century.vo.Ticket;

import java.util.List;

public interface TicketDAO {
    Ticket queryAllTickets();
    Ticket queryTicketByAirCode(String airCode);
    int addTicketBatch(List<Ticket> ticketList);
}
