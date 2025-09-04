package com.example.employee.dto;

import com.example.employee.beans.Department;
import com.example.employee.beans.Designation;
import com.example.employee.beans.EmpDeptRef;
import com.example.employee.beans.EmpDesgRef;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDetailsDto {
    @Schema(description = "Unique identifier of the employee", example = "101")
    private Long empId;
    @Schema(description = "Full name of the employee", example = "Anu")
    private String empNm;
    @Schema(description = "Location of the employee", example = "Hyderabad")
    private String location;
    @Schema(description = "Joining date of the employee", example = "2025-08-19")
    private LocalDate joiningDate;
    @Schema(description = "Salary of the employee", example = "55000.75")
    private BigDecimal salary;
    @Schema(description = "Unique number", example = "1")
    private Long orgId;
    @Schema(description = "Full name of the organisation", example = "TechCorp Pvt Ltd")
    private String orgNm;
    @Schema(description = "List of departments")
    private List<Department> empDeptList;
    @Schema(description = "List of designations")
    private List<Designation> empDesgList;



}
