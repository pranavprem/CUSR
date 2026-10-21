package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.Passenger;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PassengerDao extends CrudRepository<Passenger, Long> {

    List<Passenger> findAllByTicketId(long ticketId);
}
