package com.craft.java.lms.dto;

import lombok.Data;

import java.util.Map;

@Data
public class LeadDTO {
	private String leadId;       // e.g. "LEAD123"
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String status;       // "Open" or "Converted"
    private String source;
    private Map<String, Object> customFields;
}
