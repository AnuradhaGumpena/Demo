package com.example.employee.repository;

import com.example.employee.beans.Department;
import com.example.employee.beans.Designation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DesignationRepository {
    // Using NamedParameters
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DesignationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
// Using NamedParameters
 /*   public int save(Designation desg) {
        String sql = "INSERT INTO designation (\"desgNm\", time) VALUES (:desgNm, Now())";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desgNm", desg.getDesgNm());
        return namedParameterJdbcTemplate.update(sql, params);
    }*/

 // INSERT using Map<String,Object>
 /*   public int save(Designation desg) {
         String sql = "INSERT INTO designation (\"desgNm\", time) VALUES (:desgNm, Now())";
         Map<String, Object> params = new HashMap<>();
         params.put("desgNm", desg.getDesgNm());
         return namedParameterJdbcTemplate.update(sql, params);
    }*/

    // INSERT using BeanPropertySqlParameterSource
/*       public int save(Designation desg) {
        String sql = "INSERT INTO designation (\"desgNm\", time) VALUES (:desgNm, Now())";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(desg));
    }*/

    public Designation getDesignationById(Long desgId) {
        String sql = "SELECT \"desgId\", \"desgNm\", \"time\" " +
                "FROM designation WHERE \"desgId\" = :desgId AND \"isDeleted\" = false";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desgId", desgId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            Designation desg = new Designation();
            desg.setDesgId(rs.getLong("desgId"));
            desg.setDesgNm(rs.getString("desgNm"));
            desg.setTime(rs.getTimestamp("time").toLocalDateTime());
            return desg;
        });
    }

    public List<Designation> getAllDesignations() {
        String sql = "SELECT \"desgId\", \"desgNm\", \"time\"" +
                " FROM designation WHERE \"isDeleted\" = false";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Designation desg = new Designation();
            desg.setDesgId(rs.getLong("desgId"));
            desg.setDesgNm(rs.getString("desgNm"));
            desg.setTime(rs.getTimestamp("time").toLocalDateTime());
            return desg;
        });
    }
    //InsertOrUpdate together
    public int insertOrUpdateDesignation(Designation desg) {
        if (desg.getDesgId() == null) {
            String sql = "INSERT INTO designation (\"desgNm\", \"time\", \"isDeleted\") " +
                    "VALUES (:desgNm, NOW(), false)";
            return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(desg));
        } else {
            String sql = "UPDATE designation SET " +
                    "\"desgNm\" = :desgNm, " +
                    "\"time\" = NOW(), " +
                    "\"isDeleted\" = false " +
                    "WHERE \"desgId\" = :desgId";
            return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(desg));
        }
    }

    public int softDeleteDesignation(Long desgId) {
        String sql = "UPDATE designation SET \"isDeleted\" = true WHERE \"desgId\" = :desgId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desgId", desgId);

        return namedParameterJdbcTemplate.update(sql, params);
    }
   // Using JDBC Template
 /*  private final JdbcTemplate jdbcTemplate;

    public DesignationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Designation desg) {
        String sql = "INSERT INTO designation (\"desgNm\", time) VALUES (?,Now())";
        return jdbcTemplate.update(sql, desg.getDesgNm());
    }

    public Designation getDesignationById(Long desgId) {
        String sql = "SELECT \"desgId\", \"desgNm\", \"time\" FROM designation WHERE \"desgId\" = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{desgId}, (rs, rowNum) -> {
            Designation desg = new Designation();
            desg.setDesgId(rs.getLong("desgId"));
            desg.setDesgNm(rs.getString("desgNm"));
            desg.setTime(rs.getTimestamp("time").toLocalDateTime());
            return desg;
        });
    }

    public List<Designation> getAllDesignations() {
        String sql = "SELECT \"desgId\", \"desgNm\", \"time\" FROM designation";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Designation desg = new Designation();
            desg.setDesgId(rs.getLong("desgId"));
            desg.setDesgNm(rs.getString("desgNm"));
            desg.setTime(rs.getTimestamp("time").toLocalDateTime());
            return desg;
        });
    }*/
}
