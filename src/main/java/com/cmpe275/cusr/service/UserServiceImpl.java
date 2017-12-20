package com.cmpe275.cusr.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe275.cusr.dao.UserDao;
import com.cmpe275.cusr.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public void addUser(User user) throws Exception{
		// TODO Auto-generated method stub
		try {
			
			userDao.save(user);
			
		} catch (DataIntegrityViolationException e) {

			throw new DataIntegrityViolationException(null, null);

		} catch (Exception e) {

			throw new Exception();
		}
		
	}

	@Override
	public void deleteUser(long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			userDao.delete(id);
		
		} catch (Exception e) {

			throw new Exception();
		}
				
	}

	@Override
	public User updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			return userDao.save(user);
		
		}catch(Exception e) {
			
			throw new Exception();

		}
	}

	@Override
	public User getUser(long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return userDao.findOne(id);
		
		}catch(Exception e) {
		
			throw new Exception();

		}
	}

	@Override
	public User findbyemail(String email) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			User user = userDao.findByemail(email);
			return user;
			
		}catch(Exception e) {
			
			throw new Exception();

		}
	}	

}
