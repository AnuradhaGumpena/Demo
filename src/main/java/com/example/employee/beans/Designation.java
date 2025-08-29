package com.example.employee.beans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema
public class Designation {
    @Schema(description = "Unique identifier of the designation", example = "101")
    private Long desgId;
    @Schema(description = "Name of the designation", example = "Software Engineer")
    private String desgNm;
    @Schema(description = "current time", example = "2025-08-19 11:42:06.981954")
    private LocalDateTime time;

    private boolean isDeleted;

}
