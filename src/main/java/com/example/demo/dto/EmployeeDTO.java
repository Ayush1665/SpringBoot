package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.validation.Age;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmployeeDTO(
		Long id, 
		
		@NotBlank(message = "Name is required")
		@Pattern(regexp = "^$|^[a-zA-z ]{3,}$", message="Invalid Name")
		String name,  
		
		@Age(min = 18, max = 30, message="Employee must be between 18 and 30")
		LocalDate dob,
		
		@Valid
		EducationDTO education, 
		
		@Valid
		PostalDTO postal) {

}
