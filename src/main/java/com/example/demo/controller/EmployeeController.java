package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import com.example.demo.util.States;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

import org.springframework.data.domain.Page;
import java.util.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	    private final EmployeeService service;
	
	    public EmployeeController(EmployeeService service) {
	        this.service = service;
	    }
    
    // Common state to prevent multiple time rendering 
    @ModelAttribute("states")   // Declared it as the GLobal (Runs before every controller runs)
    public States[] populateStates() {
    	return States.values();
    }

    
    // Get all employees 5 per page and in Asc order by id
    @GetMapping
    public String getAll(
    		@RequestHeader(value = "X-employee-key", required = false) Map<String, String> employeeKey,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "5") int size,
    		@RequestParam(defaultValue = "id") String sortBy,
    		@RequestParam(defaultValue = "asc") String direction,
    		Model model
    		) {
    	
    	System.out.println("Client ID: " + employeeKey);

    	Page<EmployeeDTO> employeePage =
                service.getAllEmployees(page, size, sortBy, direction);

        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
//        model.addAttribute("states", States.values());
        
        return "employee-list";
    }
    
    @PostMapping("/bulk-delete")
    public String bulkDelete(@RequestParam(required = false) List<Long> ids,
                             RedirectAttributes redirectAttributes) {
        try {
            service.bulkDeleteEmployees(ids);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employees deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    e.getMessage());
        }

        return "redirect:/employees";
    }

    

    // Creating a New Employee
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO(null, null, null, null, null));
        return "employee-form";
    }
    
    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,	
            Model model
    ) {
        try {
            List<EmployeeDTO> results = service.searchEmployee(id, name);

            model.addAttribute("employees", results);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);

            if (results.isEmpty()) {
                model.addAttribute("searchError", "No employees found.");
            }

        } catch (Exception e) {
            model.addAttribute("employees", List.of());
            model.addAttribute("searchError", e.getMessage());
        }

        return "employee-list";
    }

    @PostMapping("/create")
    public String saveOrUpdate(
            @Valid @ModelAttribute("employee") EmployeeDTO dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
        	model.addAttribute("errors", result);
            return "employee-form"; // return same page with errors
        }
        if (dto.id() == null) {
            service.createEmployee(dto);
        } else {
            service.updateEmployee(dto.id(), dto);
        }
        return "redirect:/employees";
    }

//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable Long id) {
//        service.deleteEmployee(id);
//        return "redirect:/employees";  
//        								
//    }
    
}

// BindingResult -> Stores Validation errors and then showed in frontend.
// Works when placed after @Valid

// ModelAttribute -> Binds form data to Java Objects and add it to model.
