package com.example.demo.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model){
		model.addAttribute("message", ex.getMessage());
        return "404";
	}
}
