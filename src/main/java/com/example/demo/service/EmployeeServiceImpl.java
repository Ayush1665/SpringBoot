package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.EmployeeRepository;
import java.util.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }    
      
    @Transactional
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
    	logger.info("Creating employee with name: {}", dto.name());
    	logger.debug("Employee DOB: {}",dto.dob());
    	
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setIsActive(true);
        employee.setDob(dto.dob());
//        employee.setBloodGroup(dto.bloodGroup());

        EducationDetail edu = new EducationDetail();
        edu.setTenthPercentage(dto.education().tenthPercentage());
        edu.setTwelvethPercentage(dto.education().twelvethPercentage());
        edu.setGraduationCGPA(dto.education().graduationCGPA());
        edu.setPostGraduationCGPA(dto.education().postGraduationCGPA());
        edu.setIsActive(true);
        edu.setEmployee(employee);

        PostalDetail postal = new PostalDetail();
        postal.setCurrentAddress(dto.postal().currentAddress());
        postal.setPermanentAddress(dto.postal().permanentAddress());
        postal.setStates(dto.postal().states());
        postal.setIsActive(true);
        postal.setEmployee(employee);

        employee.setEducation(edu);
        employee.setPostal(postal);

        repository.saveEmployee(employee);
        
        logger.info("Employee created successfully with id: {}", employee.getId());
        return mapToDTO(employee);
    }
    
    
    @Transactional(readOnly = true)
    @Override
    public Page <EmployeeDTO> getAllEmployees(int page, int size, String sortBy, String direction) {
    	if (!sortBy.equals("id") && !sortBy.equals("name")) {
            sortBy = "id";
        }
    			 	
    	Sort sort = direction.equalsIgnoreCase("desc")
    			? Sort.by(sortBy).descending()
    					: Sort.by(sortBy).ascending();
    	
    	Pageable pageable = PageRequest.of(page, size, sort);
    	Page<EmployeeDTO> result = repository.findByIsActiveTrue(pageable)
                .map(this::mapToDTO);
    	
    	logger.debug("Total employees fetched: {}", result.getTotalElements());
    	return result;
    			
    }

//    @Transactional
//    @Override
//    public void deleteEmployee(Long id) {
//    	Employee employee = repository.fetchActiveEmployeeForDelete(id);
//    	
//    	if (employee == null || !employee.getIsActive()) {
//            throw new ResourceNotFoundException("User " + id + " not found.");
//        }
//
//    	// Changed tracked by Persistence Context
//        employee.setIsActive(false);
//
//        if (employee.getEducation() != null) {
//            employee.getEducation().setIsActive(false);
//        }
//
//        if (employee.getPostal() != null) {
//            employee.getPostal().setIsActive(false);
//        }
//    }

    @Transactional
    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {

        Employee employee = repository.fetchActiveEmployee(id);

        if (employee == null) {
        	logger.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);         
        }

        employee.setName(dto.name());
        employee.setDob(dto.dob());

        employee.getEducation().setTenthPercentage(dto.education().tenthPercentage());
        employee.getEducation().setTwelvethPercentage(dto.education().twelvethPercentage());
        employee.getEducation().setGraduationCGPA(dto.education().graduationCGPA());
        employee.getEducation().setPostGraduationCGPA(dto.education().postGraduationCGPA());

        employee.getPostal().setCurrentAddress(dto.postal().currentAddress());
        employee.getPostal().setPermanentAddress(dto.postal().permanentAddress());
        employee.getPostal().setStates(dto.postal().states());
        logger.info("Employee updated successfully with id: {}", id);
        return mapToDTO(employee);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> searchEmployee(Long id, String name) {

        if (id == null && (name == null || name.isBlank())) {
        	logger.warn("Invalid search request");
            throw new IllegalArgumentException("Provide id, name to search.");
        }

        List<Employee> employees = repository.searchByIdOrName(id, name);
        
        logger.debug("Search result count: {}",employees.size());

        return employees.stream()
                .map(this::mapToDTO)
                .toList();
    }

    
//    @Override
//    @Transactional(readOnly = true)
//    public EmployeeDTO getEmployeeById(Long id) {
//        Employee employee = repository.fetchActiveEmployeeById(id);
//        if (employee == null) {
//            throw new ResourceNotFoundException("Employee not found with id: " + id);
//        }
//        return mapToDTO(employee);
//    }
    
    @Transactional
    @Override
    public void bulkDeleteEmployees(List<Long> ids) {
    	if(ids == null || ids.isEmpty()) {
    		logger.warn("Bulk Delete failed - No employee selected");
    		throw new IllegalArgumentException("No employees selected");
    	}
    	repository.bulkSoftDelete(ids);
    	logger.info("Bulk delete executed successfully for {}", ids.size());
    }


    private EmployeeDTO mapToDTO(Employee e) {
        return new EmployeeDTO(
                e.getId(),
                e.getName(),
                e.getDob(),
                new EducationDTO(
                        e.getEducation().getTenthPercentage(),
                        e.getEducation().getTwelvethPercentage(),
                        e.getEducation().getGraduationCGPA(),
                        e.getEducation().getPostGraduationCGPA()
                ),
                new PostalDTO(
                        e.getPostal().getId(),
                        e.getPostal().getCurrentAddress(),
                        e.getPostal().getPermanentAddress(),
                        e.getPostal().getStates()
                )
        );
    }
}
