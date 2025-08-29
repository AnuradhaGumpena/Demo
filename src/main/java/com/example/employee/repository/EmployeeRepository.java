package com.example.employee.repository;

import com.example.employee.beans.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Employee emp) {
        String sql = "INSERT INTO employee (\"empNm\",\"location\",\"joiningDate\", \"salary\") VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql, emp.getEmpNm(), emp.getLocation(), emp.getJoiningDate(), emp.getSalary());
    }

    public Employee getEmployeeById(Long empId) {
        String sql = "SELECT \"empId\", \"empNm\",\"location\",\"joiningDate\", \"salary\" FROM employee WHERE \"empId\" = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{empId}, (rs, rowNum) -> {
            Employee emp = new Employee();
            emp.setEmpId(rs.getLong("empId"));
            emp.setEmpNm(rs.getString("empNm"));
            emp.setLocation(rs.getString("location"));
            emp.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
            emp.setSalary(rs.getBigDecimal("salary"));
            return emp;
        });
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT \"empId\", \"empNm\",\"location\",\"joiningDate\", \"salary\" FROM employee";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee emp = new Employee();
            emp.setEmpId(rs.getLong("empId"));
            emp.setEmpNm(rs.getString("empNm"));
            emp.setLocation(rs.getString("location"));
            emp.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
            emp.setSalary(rs.getBigDecimal("salary"));
            return emp;
        });
    }
}
