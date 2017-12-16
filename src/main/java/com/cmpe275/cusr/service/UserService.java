package com.cmpe275.cusr.service;

import com.cmpe275.cusr.model.User;

public interface UserService {
	
	public void addUser(User user);
	public void deleteUser(long id);
	public User updateUser(User user);
	public User getUser(long id);
	public User findbyemail(String email);
	

}
