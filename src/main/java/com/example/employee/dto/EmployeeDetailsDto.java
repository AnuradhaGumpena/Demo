package com.example.employee.dto;

import com.example.employee.beans.Department;
import com.example.employee.beans.Designation;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDetailsDto {
    private Long empId;
    private String empNm;
    private String location;
    private LocalDate joiningDate;
    private BigDecimal salary;

    private Long orgId;
    private String orgNm;
    private List<Department> empDeptList;
    private List<Designation> empDesgList;

}
