package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String to, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			javaMailSender.send(message);
			log.info("Email sent successfully to {}", to);
		} catch (Exception e) {
			log.error("Failed to send email to {}: {}", to, e.getMessage());
			// Handle the exception as needed (e.g., retry logic, alerting, etc
		}
	}
}