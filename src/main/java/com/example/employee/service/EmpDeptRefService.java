package com.example.employee.service;


import com.example.employee.beans.Department;
import com.example.employee.beans.Designation;
import com.example.employee.beans.Employee;
import com.example.employee.dto.EmployeeDetailsDto;
import com.example.employee.dto.EmployeeFilter;
import com.example.employee.repository.EmpDeptRefRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpDeptRefService {
    private final EmpDeptRefRepository repository;
    public EmpDeptRefService (EmpDeptRefRepository repository){
        this.repository=repository;
    }
    public String createEmployee(Employee emp) {
//        return repository.saveEmployee(emp);
        if (emp.getEmpId() == null) {
            // Insert case
            repository.insertOrUpdateEmployee(emp);
            return "Employee details inserted successfully";
        } else {
            // Update case
            repository.insertOrUpdateEmployee(emp);
            return "Employee details updated successfully";
        }
    }
    public EmployeeDetailsDto getEmployeeById(Long empId) {
        return repository.fetchEmployee(empId);
    }

    public boolean softDeleteEmployee(Long empId) {
        int rowsAffected = repository.softDeleteEmployee(empId);
        return rowsAffected > 0;
    }

    public List<EmployeeDetailsDto> getEmployees(EmployeeFilter filter) {
        return repository.fetchEmployees(filter);
    }
}
