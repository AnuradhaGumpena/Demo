package com.example.employee.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeFilter {
    private Long empId;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private LocalDate joiningDateFrom;
    private LocalDate joiningDateTo;
    private Long orgId;
    private String location;
}
