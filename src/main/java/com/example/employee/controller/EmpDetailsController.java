package com.example.employee.controller;

import com.example.employee.beans.Employee;
import com.example.employee.dto.EmployeeDetailsDto;
import com.example.employee.dto.EmployeeFilter;
import com.example.employee.service.EmpDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeedetails")
public class EmpDetailsController {

    private final EmpDetailService service;
    public EmpDetailsController(EmpDetailService service) {
        this.service = service;
    }

    @PostMapping
    public String createEmployee(@RequestBody Employee emp) {
       return service.createEmployee(emp);

    }
    @GetMapping("/{id}")
    public EmployeeDetailsDto getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    @PostMapping("/{empId}")
    public ResponseEntity<String> softDelete(@PathVariable Long empId) {
        boolean deleted = service.softDeleteEmployee(empId);
        if (deleted) {
            return ResponseEntity.ok("Employee soft deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }
    }

    @PostMapping("/filter")
    public List<EmployeeDetailsDto> fetchEmployees(@RequestBody EmployeeFilter filter) {
        return service.getEmployees(filter);
    }
}
