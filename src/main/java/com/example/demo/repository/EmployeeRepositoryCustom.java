package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

public interface EmployeeRepositoryCustom {

    void saveEmployee(Employee employee);

    Employee fetchActiveEmployee(Long id);

    Page<Employee> fetchAllActiveEmployees(Pageable pageable);
    
//    Employee fetchActiveEmployeeForDelete(Long id);
    
//    Employee fetchActiveEmployeeById(Long id);
    
    void bulkSoftDelete(List<Long> ids);
    
    List<Employee> searchByIdOrName(Long id, String name);
}
