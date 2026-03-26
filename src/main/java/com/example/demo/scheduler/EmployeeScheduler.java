package com.example.demo.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmailService;

@Component

public class EmployeeScheduler {
  @Autowired
  private EmailService emailService;

  @Autowired
  private EmployeeRepositoryImpl employeeRepository;

  @Scheduled(cron = "0  9 * * SUN") // Every SUNDAY at 9 AM
  public void fethchEmployeeData() {
    List<Employee> employees = employeeRepository.fetchAllActiveEmployees();
    for (Employee employee : employees) {
      String email = employee.getEmail();
      String subject = "Employee Data Update";
      String body = "Dear " + employee.getName() + ",\n\nThis is a reminder to update your employee data in the system.\n\nBest regards,\nHR Team";
      emailService.sendEmail(email, subject, body);
  }
}
