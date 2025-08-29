package com.example.employee.controller;

import com.example.employee.beans.Department;
import com.example.employee.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public String createDepartment(@RequestBody Department department) {
        return service.addDepartment(department);
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return service.getDepartmentById(id);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return service.getAllDepartments();
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<String> softDelete(@PathVariable Long deptId) {
        boolean deleted = service.softDeleteDepartment(deptId);
        if (deleted) {
            return ResponseEntity.ok("Department soft deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department not found");
        }
    }
}
