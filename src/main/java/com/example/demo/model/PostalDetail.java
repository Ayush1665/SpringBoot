package com.example.demo.model;


import com.example.demo.util.States;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "address")

public class PostalDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	
	@Column(name = "current_address", nullable = false)
	private String currentAddress;
	
	@Column(name = "permanent_address", nullable = false)
	private String permanentAddress;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private States states;

	
	
	@Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
	
	@OneToOne
	@JoinColumn(name = "employee_id", nullable = false)	 // Owning Side
	private Employee employee;

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
