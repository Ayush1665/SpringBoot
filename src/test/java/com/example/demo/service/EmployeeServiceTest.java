package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ 1. Create Employee
    @Test
    void testCreateEmployee() {

        EmployeeDTO dto = getSampleDTO();

        doAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setId(1L); // simulate DB save
            return null;
        }).when(repository).saveEmployee(any());

        EmployeeDTO result = service.createEmployee(dto);

        assertNotNull(result);
        assertEquals("Ayush", result.name());
        verify(repository).saveEmployee(any());
    }

    // ✅ 2. Get All Employees
    @Test
    void testGetAllEmployees() {

        Employee employee = getSampleEmployee();

        Page<Employee> page = new PageImpl<>(List.of(employee));

        when(repository.findByIsActiveTrue(any(Pageable.class)))
                .thenReturn(page);

        Page<EmployeeDTO> result =
                service.getAllEmployees(0, 5, "id", "asc");

        assertEquals(1, result.getContent().size());
    }

    // ✅ 3. Update Employee Success
    @Test
    void testUpdateEmployeeSuccess() {

        Employee employee = getSampleEmployee();

        when(repository.fetchActiveEmployee(1L))
                .thenReturn(employee);

        EmployeeDTO dto = getSampleDTO();

        EmployeeDTO result = service.updateEmployee(1L, dto);

        assertEquals("Ayush", result.name());
    }

    // ❌ 4. Update Employee Not Found
    @Test
    void testUpdateEmployeeNotFound() {

        when(repository.fetchActiveEmployee(1L))
                .thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () ->
                service.updateEmployee(1L, getSampleDTO())
        );
    }

    // ✅ 5. Search Employee
    @Test
    void testSearchEmployee() {

        when(repository.searchByIdOrName(any(), any()))
                .thenReturn(List.of(getSampleEmployee()));

        List<EmployeeDTO> result =
                service.searchEmployee(1L, "Ayush");

        assertEquals(1, result.size());
    }

    // ❌ 6. Search Invalid Input
    @Test
    void testSearchInvalid() {

        assertThrows(IllegalArgumentException.class, () ->
                service.searchEmployee(null, "")
        );
    }

    // ✅ 7. Bulk Delete Success
    @Test
    void testBulkDeleteSuccess() {

        service.bulkDeleteEmployees(List.of(1L, 2L));

        verify(repository).bulkSoftDelete(any());
    }

    // ❌ 8. Bulk Delete Empty
    @Test
    void testBulkDeleteEmpty() {

        assertThrows(IllegalArgumentException.class, () ->
                service.bulkDeleteEmployees(new ArrayList<>())
        );
    }


    private EmployeeDTO getSampleDTO() {
        return new EmployeeDTO(
                null,
                "Ayush",
                new Date(),
                new EducationDTO(90, 85, 8.5, 9.0),
                new PostalDTO(1L, "Delhi", "Delhi", null)
        );
    }

    private Employee getSampleEmployee() {

        Employee e = new Employee();
        e.setId(1L);
        e.setName("Ayush");
        e.setDob(new Date());
        e.setIsActive(true);

        EducationDetail edu = new EducationDetail();
        edu.setTenthPercentage(90);
        edu.setTwelvethPercentage(85);
        edu.setGraduationCGPA(8.5);
        edu.setPostGraduationCGPA(9.0);
        edu.setEmployee(e);

        PostalDetail postal = new PostalDetail();
        postal.setId(1L);
        postal.setCurrentAddress("Delhi");
        postal.setPermanentAddress("Delhi");
        postal.setEmployee(e);

        e.setEducation(edu);
        e.setPostal(postal);

        return e;
    }
}