package com.craft.java.lms.controller;

import com.craft.java.lms.dto.LeadDTO;
import com.craft.java.lms.model.Lead;
import com.craft.java.lms.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;

    // Create a new lead
    
    @PostMapping("/create")
    public ResponseEntity<?> createLead(@RequestBody LeadDTO dto) {
        try {
            LeadDTO responseDTO = leadService.createLeadAndReturnResponse(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create lead: " + e.getMessage());
        }
    }


    // Convert a lead to customer
    @PutMapping("/convert/{id}")
    public ResponseEntity<String> convertLead(@PathVariable Long id) {
        boolean success = leadService.convertLead(id);

        if (success) {
            return ResponseEntity.ok("Lead successfully converted to customer");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Lead not found or customer creation failed");
        }
    }
}
