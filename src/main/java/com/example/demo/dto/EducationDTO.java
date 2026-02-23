package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record EducationDTO(
		@NotNull(message = "Marks required!!")
		@DecimalMin(value="0.0", inclusive = true)
		@DecimalMax(value = "100.0", inclusive = true)
		Double tenthPercentage, 
		
		@NotNull(message = "Marks required!!")
		@DecimalMin(value="0.0", inclusive = true)
		@DecimalMax(value = "100.0", inclusive = true)
		Double twelvethPercentage, 
		
		@NotNull(message = "CGPA required!!")
		@DecimalMin(value="0.0", inclusive = true)
		@DecimalMax(value = "10.0", inclusive = true)
		Double graduationCGPA, 
		
		@DecimalMin(value = "0.0", inclusive = true)
		@DecimalMax(value = "10.0", inclusive = true)
		Double postGraduationCGPA
		) {
}
