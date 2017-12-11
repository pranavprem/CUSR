package com.cmpe275.cusr.service;

import com.cmpe275.cusr.model.User;

public interface userService {
	
	public void addUser(User user);
	public void deleteUser(long id);
	public User updateUser(User user);
	public User getUser(long id);

}
