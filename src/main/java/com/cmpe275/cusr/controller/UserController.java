package com.cmpe275.cusr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.cmpe275.cusr.service.EmailService;
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
	public ResponseEntity addUser(@RequestParam(value="firstName" , required=false) String firstName,
			@RequestParam(value="lastName" , required = false) String lastName,
			@RequestParam(value="email", required = true) String email,
			@RequestParam(value="password", required = true) String password,
			@RequestParam(value="token", required = false) String token) {
		
		
		try {
			
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setToken(token);
			user.setPassword(password);
		
			this.userService.addUser(user);
			return ResponseEntity.ok(user);
		
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add User Failed");

		}		
		
	}
	
	@PostMapping(value = "/userlogin")
	public ResponseEntity verifyUser(@RequestParam(value="user" , required=true) String email,
			@RequestParam(value="password" , required = true) String password) {
		
		try {
			
			User user = this.userService.findbyemail(email);		
				
			if (user.getPassword().equals(password)) {
				
				return ResponseEntity.ok("Success!");
			
			}else {
			
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Credentials!");
			
			}
			
		} catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify User Failed.");

		}
	}

	@GetMapping(value = "/sociallogin")
	public ResponseEntity socialLogin(@RequestParam("email") String email) {
		User user;
		try {
			user = userService.findbyemail(email);
		

			if (user != null)
				return ResponseEntity.ok(user);

			user = new User();
			user.setEmail(email);
			userService.addUser(user);

			return ResponseEntity.ok(userService.findbyemail(email));
		
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Social Login Failed");
			
		}
	}
	
	@GetMapping(value = "/user/{id}")
	public ResponseEntity getUser(@PathVariable("id") long userId){
		
		try {
			
			User user = this.userService.getUser(userId);
			
			if (user == null) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found!");
				
			}else {
			
				return ResponseEntity.ok(user);

			}

		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Get User Failed");
			
		}
		
	}
	
	@PutMapping(value = "/user/{id}")
	public ResponseEntity updateUser(@PathVariable("id") long userId,
			@RequestParam(value="firstName" , required=false) String firstName,
			@RequestParam(value="lastName" , required = false) String lastName,
			@RequestParam(value="email", required = false) String email,
			@RequestParam(value="token", required = false) String token){
		
			try {
				
				User user = this.userService.getUser(userId);
				
				if (user == null) {
					
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found.");
					
				}else {
				
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
					user.setToken(token);
					
					this.userService.updateUser(user);			
			
					return ResponseEntity.ok(user);
				}			
				
				
			} catch (Exception e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update User Failed");
				
			}			
	}
	
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity deleteUser(@PathVariable("id") long userId){
		
		try {
			
			User user = this.userService.getUser(userId);
			
			if (user == null) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found for delete");
				
			}else {
			
				this.userService.deleteUser(userId);
			
				return ResponseEntity.ok(user);
			
			}
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete user Failed");
			
		}		
	
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity handleMissingParams(MissingServletRequestParameterException ex) {
		
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required Parameters Missing.");

	}
	
	


}
