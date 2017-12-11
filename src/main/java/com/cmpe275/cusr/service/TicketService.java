package com.cmpe275.cusr.service;

import com.cmpe275.cusr.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(long userId);

    void addTicket(Ticket ticket);

    Ticket updateTicket(Ticket ticket);
}
