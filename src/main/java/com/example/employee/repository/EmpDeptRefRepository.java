package com.example.employee.repository;

import com.example.employee.beans.*;
import com.example.employee.dto.EmployeeDetailsDto;
import com.example.employee.dto.EmployeeFilter;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class EmpDeptRefRepository {
    //Using NamedParameters
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EmpDeptRefRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public long saveEmployee(Employee emp) {
        // Insert into Employee table
        String empSql = "INSERT INTO employee (\"empNm\", \"location\", \"joiningDate\", \"salary\", \"isDeleted\") " +
                "VALUES (:empNm, :location, :joiningDate, :salary, false)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // INSERT using BeanPropertySqlParameterSource
        BeanPropertySqlParameterSource empParams = new BeanPropertySqlParameterSource(emp);
        namedParameterJdbcTemplate.update(empSql, empParams, keyHolder, new String[]{"empId"});

        // INSERT using Map<String,Object>
    /*    Map<String, Object> empParams = new HashMap<>();
        empParams.put("empNm", emp.getEmpNm());
        empParams.put("location", emp.getLocation());
        empParams.put("joiningDate", emp.getJoiningDate());
        empParams.put("salary", emp.getSalary());
        namedParameterJdbcTemplate.update(empSql, empParams, keyHolder, new String[]{"empId"});*/

    /*    MapSqlParameterSource empParams = new MapSqlParameterSource()
                .addValue("empNm", emp.getEmpNm())
                .addValue("location", emp.getLocation())
                .addValue("joiningDate", emp.getJoiningDate())
                .addValue("salary", emp.getSalary());
        namedParameterJdbcTemplate.update(empSql, empParams, keyHolder, new String[]{"empId"});*/
        long empId = keyHolder.getKey().longValue();

        // Insert into EmpDeptRef (many departments)
        String deptSql = "INSERT INTO \"empDeptRef\" (\"empId\", \"deptId\", \"orgId\", \"currentTime\") " +
                "VALUES (:empId, :deptId, :orgId, :currentTime)";

        if (emp.getEmpdept() != null) {
            for (EmpDeptRef deptRef : emp.getEmpdept()) {
                // INSERT using Map<String,Object>
            /*    Map<String, Object> deptParams = new HashMap<>();
                deptParams.put("empId", empId);
                deptParams.put("deptId", deptRef.getDeptId());
                deptParams.put("orgId", emp.getOrgId());
                deptParams.put("currentTime", deptRef.getCurrentTime() != null ? deptRef.getCurrentTime() : LocalDateTime.now());
                namedParameterJdbcTemplate.update(deptSql, deptParams);*/

                // INSERT using BeanPropertySqlParameterSource
                BeanPropertySqlParameterSource deptParams = new BeanPropertySqlParameterSource(deptRef);
                namedParameterJdbcTemplate.update(deptSql, deptParams);

              /*  MapSqlParameterSource deptParams = new MapSqlParameterSource()
                        .addValue("empId", empId)
                        .addValue("deptId", deptRef.getDeptId())
                        .addValue("orgId", emp.getOrgId())
                        .addValue("currentTime", deptRef.getCurrentTime() != null ? deptRef.getCurrentTime() : LocalDateTime.now());
                namedParameterJdbcTemplate.update(deptSql, deptParams);*/
            }
        }

        // Insert into EmpDesgRef (many designations)
        String desgSql = "INSERT INTO \"empDesgRef\" (\"empId\", \"desgId\", \"orgId\", \"currentTime\") " +
                "VALUES (:empId, :desgId, :orgId, :currentTime)";

        if (emp.getEmpdesg() != null) {
            for (EmpDesgRef desgRef : emp.getEmpdesg()) {
                // INSERT using Map<String,Object>
             /*   Map<String, Object> desgParams = new HashMap<>();
                desgParams.put("empId", empId);
                desgParams.put("desgId", desgRef.getDesgId());
                desgParams.put("orgId", emp.getOrgId());
                desgParams.put("currentTime", desgRef.getCurrentTime() != null ? desgRef.getCurrentTime() : LocalDateTime.now());
                namedParameterJdbcTemplate.update(desgSql, desgParams);*/

                // INSERT using BeanPropertySqlParameterSource
                BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(desgRef);
                namedParameterJdbcTemplate.update(desgSql, params);

               /* MapSqlParameterSource desgParams = new MapSqlParameterSource()
                        .addValue("empId", empId)
                        .addValue("desgId", desgRef.getDesgId())
                        .addValue("orgId", emp.getOrgId())
                        .addValue("currentTime", desgRef.getCurrentTime() != null ? desgRef.getCurrentTime() : LocalDateTime.now());
                namedParameterJdbcTemplate.update(desgSql, desgParams);*/
            }
        }
        return empId;
    }

    //Using JDBC Template
  /*  private final JdbcTemplate jdbcTemplate;
    public EmpDeptRefRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
   public long saveEmployee(Employee emp) {
        // Insert into Employee table
        String empSql = "INSERT INTO employee (\"empNm\", \"location\", \"joiningDate\", \"salary\") " +
                "VALUES (?, ?, ?, ?) RETURNING \"empId\"";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(empSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, emp.getEmpNm());
            ps.setString(2, emp.getLocation());
            ps.setObject(3, emp.getJoiningDate());
            ps.setBigDecimal(4, emp.getSalary());
            return ps;
        }, keyHolder);
        long empId = keyHolder.getKey().longValue();

        // Insert into EmpDeptRef (many departments)
        String deptSql = "INSERT INTO \"empDeptRef\" (\"empId\", \"deptId\", \"orgId\", \"currentTime\") VALUES (?, ?, ?, ?)";
        if (emp.getEmpdept() != null) {
            for (EmpDeptRef deptRef : emp.getEmpdept()) {
                jdbcTemplate.update(deptSql,
                        empId,
                        deptRef.getDeptId(),
                        emp.getOrgId(),
                        deptRef.getCurrentTime() != null ? deptRef.getCurrentTime() : LocalDateTime.now());
            }
        }

        // Insert into EmpDesgRef (many designations)
        String desgSql = "INSERT INTO \"empDesgRef\" (\"empId\", \"desgId\", \"orgId\", \"currentTime\") VALUES (?, ?, ?, ?)";
        if (emp.getEmpdesg() != null) {
            for (EmpDesgRef desgRef : emp.getEmpdesg()) {
                jdbcTemplate.update(desgSql,
                        empId,
                        desgRef.getDesgId(),
                        emp.getOrgId(),
                        desgRef.getCurrentTime() != null ? desgRef.getCurrentTime() : LocalDateTime.now());
            }
        }
        return empId;
    }*/

 /*public EmployeeDetailsDto fetchEmployee(long empId) {
         // Fetch employee + organisation
         String empSql = "SELECT e.\"empId\", e.\"empNm\", e.\"location\", e.\"joiningDate\", e.\"salary\",e.\"orgId\", o.\"orgNm\" " +
                 "FROM employee e " +
                 "JOIN organisation o ON e.\"orgId\" = o.\"orgId\" " +
                 "WHERE e.\"empId\" = ?";

     EmployeeDetailsDto emp = jdbcTemplate.queryForObject(empSql, (rs, rowNum) -> {
         EmployeeDetailsDto dto = new EmployeeDetailsDto();
             dto.setEmpId(rs.getLong("empId"));
             dto.setEmpNm(rs.getString("empNm"));
             dto.setLocation(rs.getString("location"));
             dto.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
             dto.setSalary(rs.getBigDecimal("salary"));
             dto.setOrgId(rs.getLong("orgId"));
             dto.setOrgNm(rs.getString("orgNm"));
             return dto;
         }, empId);

         // Fetch departments
         String deptSql = "SELECT d.\"deptId\", d.\"deptNm\" " +
                 "FROM \"empDeptRef\" ed " +
                 "JOIN department d ON ed.\"deptId\" = d.\"deptId\" " +
                 "WHERE ed.\"empId\" = ?";

         List<Department> departments = jdbcTemplate.query(deptSql, (rs, rowNum) -> {
             Department d = new Department();
             d.setDeptId(rs.getLong("deptId"));
             d.setDeptNm(rs.getString("deptNm"));
             return d;
         }, empId);
         emp.setEmpDeptList(departments);


         // Fetch designations
         String desgSql = "SELECT g.\"desgId\", g.\"desgNm\" " +
                 "FROM \"empDesgRef\" ed " +
                 "JOIN designation g ON ed.\"desgId\" = g.\"desgId\" " +
                 "WHERE ed.\"empId\" = ?";

         List<Designation> designations = jdbcTemplate.query(desgSql, (rs, rowNum) -> {
             Designation g = new Designation();
             g.setDesgId(rs.getLong("desgId"));
             g.setDesgNm(rs.getString("desgNm"));
             return g;
         }, empId);
         emp.setEmpDesgList(designations);


         return emp;
     }*/

    public EmployeeDetailsDto fetchEmployee(long empId) {
        MapSqlParameterSource params = new MapSqlParameterSource("empId", empId);

        // Fetch employee + organisation
        String empSql = "SELECT e.\"empId\", e.\"empNm\", e.\"location\", e.\"joiningDate\", " +
                "e.\"salary\", e.\"orgId\", o.\"orgNm\" " +
                "FROM employee e " +
                "JOIN organisation o ON e.\"orgId\" = o.\"orgId\" " +
                "WHERE e.\"empId\" = :empId";

        EmployeeDetailsDto emp = namedParameterJdbcTemplate.queryForObject(empSql, params, (rs, rowNum) -> {
            EmployeeDetailsDto dto = new EmployeeDetailsDto();
            dto.setEmpId(rs.getLong("empId"));
            dto.setEmpNm(rs.getString("empNm"));
            dto.setLocation(rs.getString("location"));
            dto.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
            dto.setSalary(rs.getBigDecimal("salary"));
            dto.setOrgId(rs.getLong("orgId"));
            dto.setOrgNm(rs.getString("orgNm"));
            return dto;
        });

        // Fetch departments
        String deptSql = "SELECT d.\"deptId\", d.\"deptNm\" " +
                "FROM \"empDeptRef\" ed " +
                "JOIN department d ON ed.\"deptId\" = d.\"deptId\" " +
                "WHERE ed.\"empId\" = :empId";

        List<Department> departments = namedParameterJdbcTemplate.query(deptSql, params, (rs, rowNum) -> {
            Department d = new Department();
            d.setDeptId(rs.getLong("deptId"));
            d.setDeptNm(rs.getString("deptNm"));
            return d;
        });
        emp.setEmpDeptList(departments);

        // Fetch designations
        String desgSql = "SELECT g.\"desgId\", g.\"desgNm\" " +
                "FROM \"empDesgRef\" ed " +
                "JOIN designation g ON ed.\"desgId\" = g.\"desgId\" " +
                "WHERE ed.\"empId\" = :empId";

        List<Designation> designations = namedParameterJdbcTemplate.query(desgSql, params, (rs, rowNum) -> {
            Designation g = new Designation();
            g.setDesgId(rs.getLong("desgId"));
            g.setDesgNm(rs.getString("desgNm"));
            return g;
        });
        emp.setEmpDesgList(designations);

        return emp;
    }

    public long insertOrUpdateEmployee(Employee emp) {
        Long empId = emp.getEmpId();

        if (empId == null) {
            // INSERT Employee
            String empSql = "INSERT INTO employee (\"empNm\", \"location\", \"joiningDate\", \"salary\", \"orgId\", \"isDeleted\") " +
                    "VALUES (:empNm, :location, :joiningDate, :salary, :orgId, false)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            BeanPropertySqlParameterSource empParams = new BeanPropertySqlParameterSource(emp);

            namedParameterJdbcTemplate.update(empSql, empParams, keyHolder, new String[]{"empId"});
            empId = keyHolder.getKey().longValue();


        } else {
            // UPDATE Employee
            String updateSql = "UPDATE employee SET " +
                    "\"empNm\" = :empNm, " +
                    "\"location\" = :location, " +
                    "\"joiningDate\" = :joiningDate, " +
                    "\"salary\" = :salary, " +
                    "\"orgId\" = :orgId, " +
                    "\"isDeleted\" = false" +
                    "WHERE \"empId\" = :empId";

            BeanPropertySqlParameterSource empParams = new BeanPropertySqlParameterSource(emp);
            namedParameterJdbcTemplate.update(updateSql, empParams);
        }
// ========== EmpDeptRef InsertOrUpdate ==========
      /*  if (emp.getEmpdept() != null) {
            for (EmpDeptRef deptRef : emp.getEmpdept()) {
                deptRef.setEmpId(empId);
                if (deptRef.getCurrentTime() == null) {
                    deptRef.setCurrentTime(LocalDateTime.now());
                }

                BeanPropertySqlParameterSource deptParams = new BeanPropertySqlParameterSource(deptRef);

                // Check if record already exists
                String checkSql = "SELECT COUNT(*) FROM \"empDeptRef\" WHERE \"empId\" = :empId AND \"deptId\" = :deptId";
                Integer count = namedParameterJdbcTemplate.queryForObject(checkSql, deptParams, Integer.class);

                if (count != null && count > 0) {
                    // UPDATE
                    String updateSql = "UPDATE \"empDeptRef\" " +
                            "SET \"orgId\" = :orgId, " +
                            "\"currentTime\" = :currentTime " +
                            "WHERE \"empId\" = :empId AND \"deptId\" = :deptId";

                    namedParameterJdbcTemplate.update(updateSql, deptParams);
                    System.out.println("Updated empDeptRef for empId=" + deptRef.getEmpId() + " deptId=" + deptRef.getDeptId());
                } else {
                    //  INSERT
                    String insertSql = "INSERT INTO \"empDeptRef\" (\"empId\", \"deptId\", \"orgId\", \"currentTime\") " +
                            "VALUES (:empId, :deptId, :orgId, :currentTime)";

                    namedParameterJdbcTemplate.update(insertSql, deptParams);
                    System.out.println("Inserted into empDeptRef for empId=" + deptRef.getEmpId() + " deptId=" + deptRef.getDeptId());
                }
            }
        }*/

        if (emp.getEmpdept() != null && !emp.getEmpdept().isEmpty()) {
            // Step 1: Fetch existing deptIds for this empId in one query
            String existingSql = "SELECT \"deptId\" FROM \"empDeptRef\" WHERE \"empId\" = :empId";
            Map<String, Object> param = new HashMap<>();
            param.put("empId", empId);
            List<Long> existingDeptIds = namedParameterJdbcTemplate.queryForList(existingSql, param, Long.class);
            Set<Long> existingDeptSet = new HashSet<>(existingDeptIds);
            List<EmpDeptRef> insertList = new ArrayList<>();
            List<EmpDeptRef> updateList = new ArrayList<>();
            // Step 2: Split into update / insert lists
            for (EmpDeptRef deptRef : emp.getEmpdept()) {
                deptRef.setEmpId(empId);
                if (deptRef.getCurrentTime() == null) {
                    deptRef.setCurrentTime(LocalDateTime.now());
                }
                if (existingDeptSet.contains(deptRef.getDeptId())) {
                    updateList.add(deptRef);
                } else {
                    insertList.add(deptRef);
                }
            }
            // Step 3: Batch UPDATE
            if (!updateList.isEmpty()) {
                String updateSql = "UPDATE \"empDeptRef\" " +
                        "SET \"orgId\" = :orgId, \"currentTime\" = :currentTime " +
                        "WHERE \"empId\" = :empId AND \"deptId\" = :deptId";
                namedParameterJdbcTemplate.batchUpdate(
                        updateSql,
                        SqlParameterSourceUtils.createBatch(updateList.toArray())
                );
                System.out.println("Batch Updated: " + updateList.size() + " records in empDeptRef");
            }
            // Step 4: Batch INSERT
            if (!insertList.isEmpty()) {
                String insertSql = "INSERT INTO \"empDeptRef\" (\"empId\", \"deptId\", \"orgId\", \"currentTime\") " +
                        "VALUES (:empId, :deptId, :orgId, :currentTime)";

                namedParameterJdbcTemplate.batchUpdate(
                        insertSql,
                        SqlParameterSourceUtils.createBatch(insertList.toArray())
                );
                System.out.println("Batch Inserted: " + insertList.size() + " records in empDeptRef");
            }
        }

// ========== EmpDesgRef InsertOrUpdate ==========
      /*  if (emp.getEmpdesg() != null) {
            for (EmpDesgRef desgRef : emp.getEmpdesg()) {
                desgRef.setEmpId(empId);
                if (desgRef.getCurrentTime() == null) {
                    desgRef.setCurrentTime(LocalDateTime.now());
                }

                BeanPropertySqlParameterSource desgParams = new BeanPropertySqlParameterSource(desgRef);

                //  Check if record already exists
                String checkSql = "SELECT COUNT(*) FROM \"empDesgRef\" WHERE \"empId\" = :empId AND \"desgId\" = :desgId";
                Integer count = namedParameterJdbcTemplate.queryForObject(checkSql, desgParams, Integer.class);

                if (count != null && count > 0) {
                    // UPDATE
                    String updateSql = "UPDATE \"empDesgRef\" " +
                            "SET \"orgId\" = :orgId, " +
                            "\"currentTime\" = :currentTime " +
                            "WHERE \"empId\" = :empId AND \"desgId\" = :desgId";

                    namedParameterJdbcTemplate.update(updateSql, desgParams);
                    System.out.println("Updated empDesgRef for empId=" + desgRef.getEmpId() + " desgId=" + desgRef.getDesgId());
                } else {
                    // INSERT
                    String insertSql = "INSERT INTO \"empDesgRef\" (\"empId\", \"desgId\", \"orgId\", \"currentTime\") " +
                            "VALUES (:empId, :desgId, :orgId, :currentTime)";

                    namedParameterJdbcTemplate.update(insertSql, desgParams);
                    System.out.println("Inserted into empDesgRef for empId=" + desgRef.getEmpId() + " desgId=" + desgRef.getDesgId());
                }
            }
        }*/

        if (emp.getEmpdesg() != null && !emp.getEmpdesg().isEmpty()) {
            // Step 1: Fetch existing desgIds for this empId in one query
            String existingSql = "SELECT \"desgId\" FROM \"empDesgRef\" WHERE \"empId\" = :empId";
            Map<String, Object> param = new HashMap<>();
            param.put("empId", empId);
            List<Long> existingDesgIds = namedParameterJdbcTemplate.queryForList(existingSql, param, Long.class);
            Set<Long> existingDesgSet = new HashSet<>(existingDesgIds);
            List<EmpDesgRef> insertList = new ArrayList<>();
            List<EmpDesgRef> updateList = new ArrayList<>();
            // Step 2: Split into update / insert lists
            for (EmpDesgRef desgRef : emp.getEmpdesg()) {
                desgRef.setEmpId(empId);
                if (desgRef.getCurrentTime() == null) {
                    desgRef.setCurrentTime(LocalDateTime.now());
                }
                if (existingDesgSet.contains(desgRef.getDesgId())) {
                    updateList.add(desgRef);
                } else {
                    insertList.add(desgRef);
                }
            }
            // Step 3: Batch UPDATE
            if (!updateList.isEmpty()) {
                String updateSql = "UPDATE \"empDesgRef\" " +
                        "SET \"orgId\" = :orgId, \"currentTime\" = :currentTime " +
                        "WHERE \"empId\" = :empId AND \"desgId\" = :desgId";
                namedParameterJdbcTemplate.batchUpdate(
                        updateSql,
                        SqlParameterSourceUtils.createBatch(updateList.toArray())
                );
                System.out.println("Batch Updated: " + updateList.size() + " records in empDesgRef");
            }
            // Step 4: Batch INSERT
            if (!insertList.isEmpty()) {
                String insertSql = "INSERT INTO \"empDesgRef\" (\"empId\", \"desgId\", \"orgId\", \"currentTime\") " +
                        "VALUES (:empId, :desgId, :orgId, :currentTime)";
                namedParameterJdbcTemplate.batchUpdate(
                        insertSql,
                        SqlParameterSourceUtils.createBatch(insertList.toArray())
                );
                System.out.println("Batch Inserted: " + insertList.size() + " records in empDesgRef");
            }
        }

     /*   // ========== EmpDeptRef InsertOrUpdate ==========
        String deptSql = "INSERT INTO \"empDeptRef\" (\"empId\", \"deptId\", \"orgId\", \"currentTime\") " +
                "VALUES (:empId, :deptId, :orgId, :currentTime) " +
                "ON CONFLICT (\"empId\", \"deptId\") " +
                "DO UPDATE SET \"orgId\" = EXCLUDED.\"orgId\", " +
                "\"currentTime\" = EXCLUDED.\"currentTime\"";

        if (emp.getEmpdept() != null) {
            for (EmpDeptRef deptRef : emp.getEmpdept()) {
                deptRef.setEmpId(empId);
                if (deptRef.getCurrentTime() == null) {
                    deptRef.setCurrentTime(LocalDateTime.now());
                }
                BeanPropertySqlParameterSource deptParams = new BeanPropertySqlParameterSource(deptRef);
                namedParameterJdbcTemplate.update(deptSql, deptParams);
            }
        }

        // ========== EmpDesgRef InsertOrUpdate ==========
        String desgSql = "INSERT INTO \"empDesgRef\" (\"empId\", \"desgId\", \"orgId\", \"currentTime\") " +
                "VALUES (:empId, :desgId, :orgId, :currentTime) " +
                "ON CONFLICT (\"empId\", \"desgId\") " +
                "DO UPDATE SET \"orgId\" = EXCLUDED.\"orgId\", " +
                "\"currentTime\" = EXCLUDED.\"currentTime\"";

        if (emp.getEmpdesg() != null) {
            for (EmpDesgRef desgRef : emp.getEmpdesg()) {
                desgRef.setEmpId(empId);
                if (desgRef.getCurrentTime() == null) {
                    desgRef.setCurrentTime(LocalDateTime.now());
                }
                BeanPropertySqlParameterSource desgParams = new BeanPropertySqlParameterSource(desgRef);
                namedParameterJdbcTemplate.update(desgSql, desgParams);
            }
        }*/

        return empId;
    }


 /*public EmployeeDetailsDto fetchEmployee(long empId) {
     String sql = "SELECT e.\"empId\", e.\"empNm\", e.\"location\", e.\"joiningDate\", e.\"salary\", " +
             "e.\"orgId\", o.\"orgNm\", " +
             "d.\"deptId\", d.\"deptNm\", " +
             "g.\"desgId\", g.\"desgNm\" " +
             "FROM employee e " +
             "JOIN organisation o ON e.\"orgId\" = o.\"orgId\" " +
             "LEFT JOIN \"empDeptRef\" ed ON e.\"empId\" = ed.\"empId\" " +
             "LEFT JOIN department d ON ed.\"deptId\" = d.\"deptId\" " +
             "LEFT JOIN \"empDesgRef\" eg ON e.\"empId\" = eg.\"empId\" " +
             "LEFT JOIN designation g ON eg.\"desgId\" = g.\"desgId\" " +
             "WHERE e.\"empId\" = ?";

     EmployeeDetailsDto employee = jdbcTemplate.query(sql, new Object[]{empId}, rs -> {
         EmployeeDetailsDto dto = null;
         Set<Department> deptSet = new HashSet<>();
         Set<Designation> desgSet = new HashSet<>();

         while (rs.next()) {
             if (dto == null) {
                 BeanPropertyRowMapper<EmployeeDetailsDto> mapper =
                         new BeanPropertyRowMapper<>(EmployeeDetailsDto.class);
                 dto = mapper.mapRow(rs, 1);
             }
             if (dto == null) {
                 dto = new EmployeeDetailsDto();
                 dto.setEmpId(rs.getLong("empId"));
                 dto.setEmpNm(rs.getString("empNm"));
                 dto.setLocation(rs.getString("location"));
                 dto.setJoiningDate(rs.getDate("joiningDate").toLocalDate());
                 dto.setSalary(rs.getBigDecimal("salary"));
                 dto.setOrgId(rs.getLong("orgId"));
                 dto.setOrgNm(rs.getString("orgNm"));
             }

             Long deptId = rs.getLong("deptId");
             if (deptId != 0) {
                 Department d = new Department();
                 d.setDeptId(deptId);
                 d.setDeptNm(rs.getString("deptNm"));
                 deptSet.add(d);
             }

             Long desgId = rs.getLong("desgId");
             if (desgId != 0) {
                 Designation g = new Designation();
                 g.setDesgId(desgId);
                 g.setDesgNm(rs.getString("desgNm"));
                 desgSet.add(g);
             }
         }

         if (dto != null) {
             dto.setEmpDeptList(new ArrayList<>(deptSet));
             dto.setEmpDesgList(new ArrayList<>(desgSet));
         }
         return dto;
     });
     return employee;
 }*/

    public List<EmployeeDetailsDto> fetchEmployees(EmployeeFilter filter) {
        StringBuilder sql = new StringBuilder(
                "SELECT e.\"empId\", e.\"empNm\", e.\"location\", e.\"joiningDate\", e.\"salary\", " +
                        "e.\"orgId\", o.\"orgNm\", " +
                        "d.\"deptId\", d.\"deptNm\", " +
                        "g.\"desgId\", g.\"desgNm\" " +
                        "FROM employee e " +
                        "JOIN organisation o ON e.\"orgId\" = o.\"orgId\" " +
                        "LEFT JOIN \"empDeptRef\" ed ON e.\"empId\" = ed.\"empId\" " +
                        "LEFT JOIN department d ON ed.\"deptId\" = d.\"deptId\" " +
                        "LEFT JOIN \"empDesgRef\" eg ON e.\"empId\" = eg.\"empId\" " +
                        "LEFT JOIN designation g ON eg.\"desgId\" = g.\"desgId\" " +
                        "WHERE e.\"isDeleted\" = false "
        );

        Map<String, Object> params = new HashMap<>();

        // Priority based(apply filter)
        if (filter.getMinSalary() != null && filter.getMaxSalary() != null) {
            sql.append(" AND e.\"salary\" BETWEEN :minSalary AND :maxSalary ");
            params.put("minSalary", filter.getMinSalary());
            params.put("maxSalary", filter.getMaxSalary());
        } else if (filter.getJoiningDateFrom() != null && filter.getJoiningDateTo() != null) {
            sql.append(" AND e.\"joiningDate\" BETWEEN :fromDate AND :toDate ");
            params.put("fromDate", filter.getJoiningDateFrom());
            params.put("toDate", filter.getJoiningDateTo());
        } else if (filter.getOrgId() != null) {
            sql.append(" AND e.\"orgId\" = :orgId ");
            params.put("orgId", filter.getOrgId());
        } else if (filter.getLocation() != null && !filter.getLocation().isEmpty()) {
            sql.append(" AND e.\"location\" ILIKE :location ");
            params.put("location", "%" + filter.getLocation() + "%");
        }

        // Execute query
        return namedParameterJdbcTemplate.query(sql.toString(), params,
                new EmployeeDetailsExtractor());
    }




    public int softDeleteEmployee(Long empId) {
        String sql = "UPDATE employee SET \"isDeleted\" = true WHERE \"empId\" = :empId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("empId", empId);

        return namedParameterJdbcTemplate.update(sql, params);
    }

}



