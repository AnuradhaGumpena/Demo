package com.example.employee.repository;

import com.example.employee.beans.Department;
import com.example.employee.beans.Designation;
import com.example.employee.dto.EmployeeDetailsDto;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EmployeeDetailsExtractor implements RowMapper<EmployeeDetailsDto> {

    // Map to aggregate employees
    private final Map<Long, EmployeeDetailsDto> employeeMap = new LinkedHashMap<>();

    @Override
    public EmployeeDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long empId = rs.getLong("empId");
        EmployeeDetailsDto empDto = employeeMap.get(empId);

        if (empDto == null) {
            empDto = new EmployeeDetailsDto();
            empDto.setEmpId(empId);
            empDto.setEmpNm(rs.getString("empNm"));
            empDto.setLocation(rs.getString("location"));
            empDto.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
            empDto.setSalary(BigDecimal.valueOf(rs.getDouble("salary")));
            empDto.setOrgId(rs.getLong("orgId"));
            empDto.setOrgNm(rs.getString("orgNm"));
            empDto.setEmpDeptList(new ArrayList<>());
            empDto.setEmpDesgList(new ArrayList<>());

            employeeMap.put(empId, empDto);
        }

        // Add department if not already present
        Long deptId = rs.getLong("deptId");
        String deptNm = rs.getString("deptNm");
        if (deptId != 0 && deptNm != null) {
            boolean exists = empDto.getEmpDeptList().stream()
                    .anyMatch(d -> d.getDeptId().equals(deptId));
            if (!exists) {
//                empDto.getEmpDeptList().add(new Department());
                Department dept = new Department();
                dept.setDeptId(deptId);
                dept.setDeptNm(deptNm);
                empDto.getEmpDeptList().add(dept);

            }
        }

        // Add designation if not already present
        Long desgId = rs.getLong("desgId");
        String desgNm = rs.getString("desgNm");
        if (desgId != 0 && desgNm != null) {
            boolean exists = empDto.getEmpDesgList().stream()
                    .anyMatch(d -> d.getDesgId().equals(desgId));
            if (!exists) {
//                empDto.getEmpDesgList().add(new Designation());

                Designation desg = new Designation();
                desg.setDesgId(desgId);
                desg.setDesgNm(desgNm);
                empDto.getEmpDesgList().add(desg);
            }
        }

        // Return null for individual rows; final list is collected separately
        return null;
    }

    public List<EmployeeDetailsDto> getEmployees() {
        return new ArrayList<>(employeeMap.values());
    }
}

