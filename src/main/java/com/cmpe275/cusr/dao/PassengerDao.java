package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.Passenger;
import com.cmpe275.cusr.model.TrainKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassengerDao extends CrudRepository<Passenger, Long> {

    List<Passenger> findAllByTicketId(long ticketId);
}
