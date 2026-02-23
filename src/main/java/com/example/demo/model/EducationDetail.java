package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "education")

public class EducationDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name="x_class")
	private Double tenthPercentage;
	
	@Column(name = "xii_class")
	private Double twelvethPercentage;
	
	@Version
	private Long version;   
	
	@Column(name = "graduation")
	private Double graduationCGPA;
	
	@Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
	
	@Column(name = "post_graduation")
	private Double postGraduationCGPA;
	
	
	@OneToOne
	@JoinColumn(name = "employee_id", nullable = false) // owning side
	private Employee employee;

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}	
}
