package com.netcracker.ageev.library.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AgeLimitDTO {
    private Integer id;
    private String ageLimit;
    private Date created;
    private Date updated;
    private String createdBy;
    private String updatedBy;
}
