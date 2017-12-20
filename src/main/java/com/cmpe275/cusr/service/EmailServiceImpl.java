package com.cmpe275.cusr.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String from, String to, String title, String body) {
        
        try {
        	MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
            
            if (from != null) {
                mimeMessageHelper.setFrom(from);
            }
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(body,true);
            mimeMessageHelper.setTo(to);
            
            this.javaMailSender.send(message);
        } catch (MessagingException messageException) {
            // You could also 'throw' this exception. I am not a fan of checked exceptions. 
            // If you want to go that route, then just update this method and the interface. 
            throw new RuntimeException(messageException);
        }
    }
	
	
	

}
