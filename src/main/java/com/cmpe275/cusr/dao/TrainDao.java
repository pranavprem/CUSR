package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainDao extends CrudRepository<Train,String> {

}
