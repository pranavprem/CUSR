package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.TicketDetail;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TicketDetailDao extends CrudRepository<TicketDetail, Long> {

    List<TicketDetail> findAllByTicketId(long ticketId);

    List<TicketDetail> findAllByTrainId(String trainId);
}
