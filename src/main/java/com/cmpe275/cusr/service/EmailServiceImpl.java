package com.cmpe275.cusr.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final ObjectProvider<JavaMailSender> javaMailSender;

    @Value("${cusr.mail.from:noreply@localhost}")
    private String defaultFrom;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    public EmailServiceImpl(ObjectProvider<JavaMailSender> javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String from, String to, String title, String body) {
        JavaMailSender sender = javaMailSender.getIfAvailable();
        if (sender == null || mailUsername == null || mailUsername.isBlank()) {
            log.info("SMTP is not configured; not sending '{}' to {}", title, to);
            return;
        }

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
            String senderAddress = (from == null || from.isBlank()) ? defaultFrom : from;
            mimeMessageHelper.setFrom(senderAddress);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.setTo(to);
            sender.send(message);
        } catch (MessagingException messageException) {
            throw new RuntimeException(messageException);
        }
    }
}
