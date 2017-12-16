package com.cmpe275.cusr.service;

import com.cmpe275.cusr.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(long userId);

    Ticket addTicket(Ticket ticket);

    Ticket deleteTicket(long ticketId);

    List<Ticket> getAllTicket();
}
