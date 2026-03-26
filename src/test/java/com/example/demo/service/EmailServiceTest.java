package com.example.demo.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class EmailServiceTest {
  @Autowired  
  private EmailService emailService;

  @Test
  void testSendEmail() {
    emailService.sendEmail("xyz@gmail.com", "Test Subject", "This is a test email body.");
  }
    
}
