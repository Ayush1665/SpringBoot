package com.example.demo.service;

import org.springframework.data.domain.Page;
import com.example.demo.dto.EmployeeDTO;
import java.util.*;

public interface EmployeeService {
	 EmployeeDTO createEmployee(EmployeeDTO dto);
	 Page<EmployeeDTO> getAllEmployees(int page, int size, String sortBy, String direction);
	 EmployeeDTO updateEmployee(Long id, EmployeeDTO dto);
//	 void deleteEmployee(Long id);
//	 EmployeeDTO getEmployeeById(Long id);
	 void bulkDeleteEmployees(List<Long> ids);
	 List<EmployeeDTO> searchEmployee(Long id, String name);

}
