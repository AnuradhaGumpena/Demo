package com.example.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeFilter {
    @Schema(description = "Unique identifier of the employee", example = "101")
    private Long empId;
    @Schema(description = "minimum Salary of the employee", example = "25000.75")
    private BigDecimal minSalary;
    @Schema(description = "maximum Salary of the employee", example = "85000.75")
    private BigDecimal maxSalary;
    @Schema(description = "Joining date from of the employee", example = "2025-08-19")
    private LocalDate joiningDateFrom;
    @Schema(description = "Joining date to of the employee", example = "2025-08-19")
    private LocalDate joiningDateTo;
    @Schema(description = "Unique number", example = "1")
    private Long orgId;
    @Schema(description = "Location of the employee", example = "Hyderabad")
    private String location;
}
