package com.cmpe275.cusr.service;

import com.cmpe275.cusr.dao.TicketDao;
import com.cmpe275.cusr.model.Ticket;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketDao ticketDao;

    @Override
    public List<Ticket> getTickets(long userId) {
        return ticketDao.findAllByUserId(userId);
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    @Override
    public Ticket deleteTicket(long ticketId) {
        Ticket ticket = ticketDao.findOne(ticketId);

        if (ticket == null)
            return null;

        DateTime departureTime = new DateTime(Long.parseLong(ticket.getDepartureTime())*1000L);
        if (departureTime.minusHours(1).isAfterNow()) {
            ticketDao.delete(ticketId);

            return ticket;
        } else throw new IllegalStateException();
    }

    @Override
    public List<Ticket> getAllTicket() {
        return (List<Ticket>) ticketDao.findAll();
    }
}
