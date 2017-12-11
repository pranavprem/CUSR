package com.cmpe275.cusr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmpe275.cusr.model.User;
import com.cmpe275.cusr.service.userService;


public class UserController {
	
	@Autowired
	private userService userService;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public User addUser(@RequestParam(value="firstName") String firstName ) {
		
		User user = new User();
		user.setFirstName(firstName);
		this.userService.addUser(user);
		return user;
	}
	

}
