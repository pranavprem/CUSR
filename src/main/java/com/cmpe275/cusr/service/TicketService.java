package com.cmpe275.cusr.service;

import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.model.Train;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(long userId);

    Ticket addTicket(Ticket ticket);

    Ticket deleteTicket(long ticketId);

    List<Ticket> getAllTicket();

    void resetData(long seats);

    long getSeatAvailable(String trainId);

    void cancelTrain(String trainId);

    Ticket getTicketDetail(long ticketId);
}
