package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "employee", uniqueConstraints = @UniqueConstraint(
		columnNames = {"name","dob"}
		))
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
	
	@Version
	private Long version;   // Value is null -> Long After Insertion 
	
	@Column(nullable = false)
	private String name;
	
	@Column(name = "dob")
	private LocalDate dob;

	@Column(unique = true, nullable = false)
	private String email;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL) // Inverse side
	private EducationDetail education;
	
	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)  // Inverse Side
	private PostalDetail postal;
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
