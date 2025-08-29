package com.example.employee.repository;

import com.example.employee.beans.Department;
import com.example.employee.beans.Designation;
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
public class OrganisationRepository {
    //Using NamedParameters
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrganisationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    //Using NamedParameters
/*    public int save(Organisation org) {
        String sql = "INSERT INTO organisation (\"orgNm\", \"address\", \"contactNo\", time) " +
                "VALUES (:orgNm, :address, :contactNo, Now())";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgNm", org.getOrgNm())
                .addValue("address", org.getAddress())
                .addValue("contactNo", org.getContactNo());
        return namedParameterJdbcTemplate.update(sql, params);
    }*/

    // INSERT using Map<String,Object>
 /*   public int save(Organisation org) {
        String sql = "INSERT INTO organisation (\"orgNm\", \"address\", \"contactNo\", time) " +
                "VALUES (:orgNm, :address, :contactNo, Now())";
        Map<String, Object> params = new HashMap<>();
        params.put("orgNm", org.getOrgNm());
        params.put("address", org.getAddress());
        params.put("contactNo", org.getContactNo());
        return namedParameterJdbcTemplate.update(sql, params);
    }*/

    // INSERT using BeanPropertySqlParameterSource
  /*  public int save(Organisation org) {
        String sql = "INSERT INTO organisation (\"orgNm\", \"address\", \"contactNo\", time) " +
                "VALUES (:orgNm, :address, :contactNo, Now())";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(org));
    }*/

    public Organisation getOrganisationById(Long orgId) {
        String sql = "SELECT \"orgId\", \"orgNm\", \"address\", \"contactNo\", \"time\" " +
                "FROM organisation WHERE \"orgId\" = :orgId AND \"isDeleted\" = false";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgId", orgId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            Organisation org = new Organisation();
            org.setOrgId(rs.getLong("orgId"));
            org.setOrgNm(rs.getString("orgNm"));
            org.setAddress(rs.getString("address"));
            org.setContactNo(rs.getString("contactNo"));
            org.setTime(rs.getTimestamp("time").toLocalDateTime());
            return org;
        });
    }

    public List<Organisation> getAllOrganisations() {
        String sql = "SELECT \"orgId\", \"orgNm\", \"address\", \"contactNo\", \"time\" " +
                "FROM organisation WHERE \"isDeleted\" = false";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Organisation org = new Organisation();
            org.setOrgId(rs.getLong("orgId"));
            org.setOrgNm(rs.getString("orgNm"));
            org.setAddress(rs.getString("address"));
            org.setContactNo(rs.getString("contactNo"));
            org.setTime(rs.getTimestamp("time").toLocalDateTime());
            return org;
        });
    }

    //InsertOrUpdate together
    public int insertOrUpdateOrganisation(Organisation org) {
        if (org.getOrgId() == null) {
            String sql = "INSERT INTO organisation (\"orgNm\",\"address\", \"contactNo\", \"time\", \"isDeleted\") " +
                    "VALUES (:orgNm, :address, :contactNo, NOW(), false) ";
            return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(org));
        } else {
            String sql = "UPDATE organisation SET " +
                    "\"orgNm\" = :orgNm, " +
                    "\"address\" = :address, " +
                    "\"contactNo\" = :contactNo, " +
                    "\"time\" = NOW(), " +
                    "\"isDeleted\" = false " +
                    "WHERE \"orgId\" = :orgId";
            return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(org));
        }
    }

    public int softDeleteOrganisation(Long orgId) {
        String sql = "UPDATE organisation SET \"isDeleted\" = true WHERE \"orgId\" = :orgId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orgId", orgId);

        return namedParameterJdbcTemplate.update(sql, params);
    }

   // Using JDBC Template
  /*   private final JdbcTemplate jdbcTemplate;
    public OrganisationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Organisation org) {
        String sql = "INSERT INTO organisation (\"orgNm\",\"address\",\"contactNo\", time) VALUES (?,?,?,Now())";
        return jdbcTemplate.update(sql, org.getOrgNm(), org.getAddress(), org.getContactNo());
    }

    public Organisation getOrganisationById(Long orgId) {
        String sql = "SELECT \"orgId\", \"orgNm\",\"address\",\"contactNo\",\"time\" FROM organisation WHERE \"orgId\" = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orgId}, (rs, rowNum) -> {
            Organisation org = new Organisation();
            org.setOrgId(rs.getLong("orgId"));
            org.setOrgNm(rs.getString("orgNm"));
            org.setAddress(rs.getString("address"));
            org.setContactNo(rs.getString("contactNo"));
            org.setTime(rs.getTimestamp("time").toLocalDateTime());
            return org;
        });
    }

    public List<Organisation> getAllOrganisations() {
        String sql = "SELECT \"orgId\", \"orgNm\",\"address\",\"contactNo\",\"time\" FROM organisation";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Organisation org = new Organisation();
            org.setOrgId(rs.getLong("orgId"));
            org.setOrgNm(rs.getString("orgNm"));
            org.setAddress(rs.getString("address"));
            org.setContactNo(rs.getString("contactNo"));
            org.setTime(rs.getTimestamp("time").toLocalDateTime());
            return org;
        });
    }*/
}
