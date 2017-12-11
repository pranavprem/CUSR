package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketDao extends CrudRepository<Ticket, Long> {

    @Query("select Ticket from ticket t where t.userId = ?1")
    List<Ticket> findAllByUserId(long id);
}
