package com.example.employee.service;

import com.example.employee.beans.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService (EmployeeRepository repository){
        this.repository=repository;
    }

    public void addEmployee(Employee employee){
        repository.save(employee);
    }

   public Employee getEmployeeById(Long empId) {
        return repository.getEmployeeById(empId);
    }

    public List<Employee> getAllEmployees() {
        return repository.getAllEmployees();
    }


}
