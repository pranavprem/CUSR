package com.cmpe275.cusr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.cusr.dao.UserDao;
import com.cmpe275.cusr.model.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
		
	}

	@Override
	public void deleteUser(long id) {
		// TODO Auto-generated method stub
		userDao.delete(id);
				
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
		//return null;
	}

	@Override
	public User getUser(long id) {
		// TODO Auto-generated method stub
		return userDao.findOne(id);
	}
	

}
