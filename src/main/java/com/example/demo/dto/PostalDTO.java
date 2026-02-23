package com.example.demo.dto;

import com.example.demo.util.States;

import jakarta.validation.constraints.NotBlank;

public record PostalDTO(Long id, 
		@NotBlank(message = "Current Address is required!!")
		String currentAddress, 
		
		@NotBlank(message = "Permanent Address is required!!")
		String permanentAddress,

		States states
		) {
}
