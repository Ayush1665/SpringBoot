package com.example.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Age {
	String message() default "Age must be between {min} and {max} years";
	int min() default 18;    // good to give default
	int max() default 60;
	Class<?> [] groups() default{};
	Class<? extends Payload>[] payload() default {};  
}
