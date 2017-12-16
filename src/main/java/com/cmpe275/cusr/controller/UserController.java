package com.cmpe275.cusr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.cusr.model.User;
import com.cmpe275.cusr.service.UserService;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		return "hello";
	}
	
	@PostMapping(value = "/user")
	public ResponseEntity<?> addUser(@RequestParam(value="firstName" , required=false) String firstName,
			@RequestParam(value="lastName" , required = false) String lastName,
			@RequestParam(value="email", required = true) String email,
			@RequestParam(value="password", required = true) String password,
			@RequestParam(value="token", required = false) String token) {
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setToken(token);
		user.setPassword(password);
		
		this.userService.addUser(user);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping(value = "/userlogin")
	public ResponseEntity<?> verifyUser(@RequestParam(value="user" , required=true) String email,
			@RequestParam(value="password" , required = true) String password) {
		
		User user = this.userService.findbyemail(email);
				
		if (user.getPassword().equals(password)) {
			
			return ResponseEntity.ok("Success!");
			
		}else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Credentials!");
		}
	}
	
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") long userId){
		
		User user = this.userService.getUser(userId);
		
		return ResponseEntity.ok(user);
	}
	
	@PutMapping(value = "/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") long userId,
			@RequestParam(value="firstName" , required=false) String firstName,
			@RequestParam(value="lastName" , required = false) String lastName,
			@RequestParam(value="email", required = false) String email,
			@RequestParam(value="token", required = false) String token){
		
			User user = this.userService.getUser(userId);
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setToken(token);
			
			this.userService.updateUser(user);			
	
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long userId){
		
		User user = this.userService.getUser(userId);
		
		this.userService.deleteUser(userId);
		
		return ResponseEntity.ok(user);
	
	}

}
