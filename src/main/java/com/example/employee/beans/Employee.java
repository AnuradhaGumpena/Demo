package com.example.employee.beans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Schema
public class Employee {
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
    private List<EmpDeptRef> empdept;
    private List<EmpDesgRef> empdesg;
    @Schema(description = "Unique number", example = "1")
    private long orgId;
    private boolean isDeleted;

}
