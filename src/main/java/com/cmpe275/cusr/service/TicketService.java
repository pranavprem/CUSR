package com.cmpe275.cusr.service;

import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.model.Train;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(long userId) throws Exception;

    Ticket addTicket(Ticket ticket) throws Exception;

    Ticket deleteTicket(long ticketId) throws Exception;

    List<Ticket> getAllTicket() throws Exception;

    void resetData(long seats) throws Exception;

    long getSeatAvailable(String trainId) throws Exception;

    void cancelTrain(String trainId) throws Exception;

    Ticket getTicketDetail(long ticketId) throws Exception;
    
    String getEmailBody(Ticket ticket) throws Exception;
}
