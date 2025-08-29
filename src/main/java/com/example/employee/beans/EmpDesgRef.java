package com.example.employee.beans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema
public class EmpDesgRef {
    @Schema(description = "Unique identifier", example = "1")
    private long id;
    @Schema(description = "Unique identifier of the employee", example = "101")
    private long empId;
    @Schema(description = "Unique identifier of the designation", example = "101")
    private long desgId;
    @Schema(description = "Unique identifier of the organisation", example = "101")
    private long orgId;
    @Schema(description = "current time", example = "2025-08-19 11:42:06.981954")
    private LocalDateTime currentTime;
}
