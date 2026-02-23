package com.example.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
	 Page<Employee> findByIsActiveTrue(Pageable pageable);
}
