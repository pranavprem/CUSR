package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketDao extends CrudRepository<Ticket, Long> {
}
