package com.example.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Age {
	String message() default "Age must be between {min} and {max} years";
	int min();
	int max();
	Class<?> [] groups() default{};
	Class<? extends Payload>[] payload() default {};  // 
}
