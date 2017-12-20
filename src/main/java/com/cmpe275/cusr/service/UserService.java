package com.cmpe275.cusr.service;

import com.cmpe275.cusr.model.User;

public interface UserService {
	
	public void addUser(User user) throws Exception;
	public void deleteUser(long id) throws Exception;
	public User updateUser(User user) throws Exception;
	public User getUser(long id) throws Exception;
	public User findbyemail(String email) throws Exception;
	

}
