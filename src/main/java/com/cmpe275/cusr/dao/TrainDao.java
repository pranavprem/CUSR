package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.Train;
import com.cmpe275.cusr.model.TrainKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainDao extends CrudRepository<Train,String> {

    List<Train> findAllByTicket(long ticketId);
}
