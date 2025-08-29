package com.example.employee.repository;


import com.example.employee.beans.Department;
import com.example.employee.beans.Designation;
import com.example.employee.dto.EmployeeDetailsDto;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDetailsExtractor implements ResultSetExtractor<List<EmployeeDetailsDto>> {
    @Override
    public List<EmployeeDetailsDto> extractData(ResultSet rs) throws SQLException {
        Map<Long, EmployeeDetailsDto> map = new HashMap<>();

        while (rs.next()) {
            long empId = rs.getLong("empId");
            EmployeeDetailsDto dto = map.computeIfAbsent(empId, id -> {
                EmployeeDetailsDto e = new EmployeeDetailsDto();
                try {
                    e.setEmpId(id);
                    e.setEmpNm(rs.getString("empNm"));
                    e.setLocation(rs.getString("location"));
                    e.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
                    e.setSalary(rs.getBigDecimal("salary"));
                    e.setOrgId(rs.getLong("orgId"));
                    e.setOrgNm(rs.getString("orgNm"));
                    e.setEmpDeptList(new ArrayList<>());
                    e.setEmpDesgList(new ArrayList<>());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return e;
            });

            long deptId = rs.getLong("deptId");
            if (deptId != 0) {
                Department d = new Department();
                d.setDeptId(deptId);
                d.setDeptNm(rs.getString("deptNm"));
                dto.getEmpDeptList().add(d);
            }

            long desgId = rs.getLong("desgId");
            if (desgId != 0) {
                Designation g = new Designation();
                g.setDesgId(desgId);
                g.setDesgNm(rs.getString("desgNm"));
                dto.getEmpDesgList().add(g);
            }
        }

        return new ArrayList<>(map.values());
    }
}

