package com.example.employee.repository;

import com.example.employee.beans.Department;
import com.example.employee.beans.Organisation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DepartmentRepository {
    // Using NamedParameters
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
// Using NamedParameters
  /*  public int save(Department dept) {
        String sql = "INSERT INTO department (\"deptNm\", \"description\", time) VALUES (:deptNm,:description, Now())";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("deptNm", dept.getDeptNm())
                .addValue("description",dept.getDescription());
        return namedParameterJdbcTemplate.update(sql, params);
    }*/

    // INSERT using Map<String,Object>
   /* public int save(Department dept) {
        String sql = "INSERT INTO department (\"deptNm\", \"description\", time) " +
                "VALUES (:deptNm,:description, Now())";
        Map<String, Object> params = new HashMap<>();
        params.put("deptNm", dept.getDeptNm());
        params.put("description", dept.getDescription());
        return namedParameterJdbcTemplate.update(sql, params);
    }*/

    // INSERT using BeanPropertySqlParameterSource
 /*   public int save(Department dept) {
        String sql = "INSERT INTO department (\"deptNm\", \"description\", time) " +
                "VALUES (:deptNm,:description, Now())";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(dept));
    }*/

    public Department getDepartmentById(Long deptId) {
        String sql = "SELECT \"deptId\", \"deptNm\", \"time\" " +
                "FROM department WHERE \"deptId\" = :deptId AND \"isDeleted\" = false";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("deptId", deptId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            Department dept = new Department();
            dept.setDeptId(rs.getLong("deptId"));
            dept.setDeptNm(rs.getString("deptNm"));
            dept.setDescription(rs.getString("description"));
            dept.setTime(rs.getTimestamp("time").toLocalDateTime());
            return dept;
        });
    }

    public List<Department> getAllDepartments() {
        String sql = "SELECT \"deptId\", \"deptNm\",\"description\", \"time\" " +
                "FROM department WHERE \"isDeleted\" = false";

        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Department dept = new Department();
            dept.setDeptId(rs.getLong("deptId"));
            dept.setDeptNm(rs.getString("deptNm"));
            dept.setDescription(rs.getString("description"));
            dept.setTime(rs.getTimestamp("time").toLocalDateTime());
            return dept;
        });
    }

    //InsertOrUpdate together
    public int insertOrUpdateDepartment(Department dept) {
        if (dept.getDeptId() == null) {
        String sql = "INSERT INTO department (\"deptNm\", \"description\", \"time\", \"isDeleted\") " +
                "VALUES (:deptNm, :description, NOW(), false)";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(dept));

    } else {
        String sql = "UPDATE department SET " +
                "\"deptNm\" = :deptNm, " +
                "\"description\" = :description, " +
                "\"time\" = NOW(), " +
                "\"isDeleted\" = false " +
                "WHERE \"deptId\" = :deptId";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(dept));
    }
    }

    public int softDeleteDepartment(Long deptId) {
        String sql = "UPDATE department SET \"isDeleted\" = true WHERE \"deptId\" = :deptId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("deptId", deptId);

        return namedParameterJdbcTemplate.update(sql, params);
    }

    //Using JDBC Template
  /* private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Department dept) {
        String sql = "INSERT INTO department (\"deptNm\",\"description\", time) VALUES (?,Now())";
        return jdbcTemplate.update(sql, dept.getDeptNm(), dept.getDescription());
    }

    public Department getDepartmentById(Long deptId) {
        String sql = "SELECT \"deptId\", \"deptNm\",\"description\", \"time\" FROM department WHERE \"deptId\" = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{deptId}, (rs, rowNum) -> {
            Department dept = new Department();
            dept.setDeptId(rs.getLong("deptId"));
            dept.setDeptNm(rs.getString("deptNm"));
            dept.setDescription(rs.getString("description"));
            dept.setTime(rs.getTimestamp("time").toLocalDateTime());
            return dept;
        });
    }

    public List<Department> getAllDepartments() {
        String sql = "SELECT \"deptId\", \"deptNm\",\"description\", \"time\" FROM department";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Department dept = new Department();
            dept.setDeptId(rs.getLong("deptId"));
            dept.setDeptNm(rs.getString("deptNm"));
             dept.setDescription(rs.getString("description"));
            dept.setTime(rs.getTimestamp("time").toLocalDateTime());
            return dept;
        });
    }*/
}
