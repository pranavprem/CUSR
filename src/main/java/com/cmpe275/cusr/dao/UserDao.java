package com.cmpe275.cusr.dao;

import com.cmpe275.cusr.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>{
	

}
