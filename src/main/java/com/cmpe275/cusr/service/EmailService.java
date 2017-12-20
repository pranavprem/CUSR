package com.cmpe275.cusr.service;

public interface EmailService {
	
	 void send(String from, String to, String title, String body);

}
