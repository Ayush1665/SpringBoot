package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    // ✅ 1. Test GET /employees
    @Test
    void testGetAllEmployees() throws Exception {

        List<EmployeeDTO> employees = List.of(
                new EmployeeDTO(1L, "Ayush", "Delhi", "IT", "Active")
        );

        Page<EmployeeDTO> page = new PageImpl<>(employees);

        Mockito.when(service.getAllEmployees(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(page);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-list"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"));
    }

    // ✅ 2. Test POST /employees/create (valid case)
    @Test
    void testCreateEmployee() throws Exception {

        mockMvc.perform(post("/employees/create")
                        .param("name", "Ayush")
                        .param("state", "Delhi"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));

        Mockito.verify(service).createEmployee(any());
    }

    // ✅ 3. Test POST /employees/create (validation error)
    @Test
    void testCreateEmployeeValidationFail() throws Exception {

        mockMvc.perform(post("/employees/create")
                        .param("name", "")) // invalid
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"));

        Mockito.verify(service, Mockito.never()).createEmployee(any());
    }

    // ✅ 4. Test Search
    @Test
    void testSearchEmployee() throws Exception {

        List<EmployeeDTO> employees = List.of(
                new EmployeeDTO(1L, "Ayush", "Delhi", "IT", "Active")
        );

        Mockito.when(service.searchEmployee(any(), any()))
                .thenReturn(employees);

        mockMvc.perform(get("/employees/search")
                        .param("name", "Ayush"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-list"))
                .andExpect(model().attributeExists("employees"));
    }

    // ✅ 5. Test Bulk Delete (success)
    @Test
    void testBulkDeleteSuccess() throws Exception {

        mockMvc.perform(post("/employees/bulk-delete")
                        .param("ids", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));

        Mockito.verify(service).bulkDeleteEmployees(any());
    }

    // ✅ 6. Test Bulk Delete (exception case)
    @Test
    void testBulkDeleteException() throws Exception {

        Mockito.doThrow(new IllegalArgumentException("Invalid IDs"))
                .when(service).bulkDeleteEmployees(any());

        mockMvc.perform(post("/employees/bulk-delete")
                        .param("ids", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"));
    }
}