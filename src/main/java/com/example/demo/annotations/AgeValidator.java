package com.example.demo.validation;

import java.time.LocalDate;
import java.time.Period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Age, LocalDate>{
	private int min;
	private int max;
	
	@Override
	public void initialize(Age constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}
	
	@Override
	public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
		if(dob == null) {
			return true;
		} 
		int age = Period.between(dob, LocalDate.now()).getYears();
		return age >= min && age <= max;
	}
}
