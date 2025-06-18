package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public String sendMail(String to,String sub,String data) throws MessagingException {
		
		MimeMessage msg = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		helper.setTo(to);
		helper.setSubject(sub);
		helper.setText("<h1>HELLO<h1>",true);
		
		
		
		mailSender.send(msg);
		
		return "";
	}
	
}
