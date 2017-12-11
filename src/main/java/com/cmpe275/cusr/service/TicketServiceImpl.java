package com.cmpe275.cusr.service;

import com.cmpe275.cusr.dao.TicketDao;
import com.cmpe275.cusr.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketDao ticketDao;

    @Override
    public List<Ticket> getTickets(long userId) {
        return null;
    }

    @Override
    public void addTicket(Ticket ticket) {
        ticketDao.save(ticket);
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return ticketDao.save(ticket);
    }
}
