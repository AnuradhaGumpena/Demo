package com.example.employee.service;

import com.example.employee.beans.Department;
import com.example.employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public String addDepartment(Department department) {
//        repository.save(department);
        if (department.getDeptId() == null) {
            // Insert case
            repository.insertOrUpdateDepartment(department);
            return "Department inserted successfully";
        } else {
            // Update case
            repository.insertOrUpdateDepartment(department);
            return "Department updated successfully";
        }
    }

    public Department getDepartmentById(Long deptId) {
        return repository.getDepartmentById(deptId);
    }

    public List<Department> getAllDepartments() {
        return repository.getAllDepartments();
    }

    public boolean softDeleteDepartment(Long deptId) {
        int rowsAffected = repository.softDeleteDepartment(deptId);
        return rowsAffected > 0;
    }
}
