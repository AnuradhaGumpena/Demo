package com.example.employee.beans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema
public class Organisation {
    @Schema(description = "Unique identifier of the organisation", example = "101")
    private Long orgId;
    @Schema(description = "Name of the organisation", example = "TechCorp Pvt Ltd")
    private String orgNm;
    @Schema(description = "Address of the organisation", example = "123, Park Street, Hyderabad")
    private String address;
    @Schema(description = "Contact number", example = "+91-9876543210")
    private String contactNo;
    @Schema(description = "current time", example = "2025-08-19 11:42:06.981954")
    private LocalDateTime time;
    @Schema(description = "Soft Delete", example = "False")
    private boolean isDeleted;
}
