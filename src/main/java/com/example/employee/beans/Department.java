package com.example.employee.beans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Schema
public class Department {
    @Schema(description = "Unique identifier of the department", example = "101")
    private Long deptId;
    @Schema(description = "Name of the department", example = "Human Resources")
    private String deptNm;
    @Schema(description = "description for the department", example = "Handles employee relations & payroll")
    private String description;
    @Schema(description = "current time", example = "2025-08-19 11:42:06.981954")
    private LocalDateTime time;

    private boolean isDeleted;
}
