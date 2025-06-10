package com.craft.java.lms.service;

import com.craft.java.lms.client.CmsClient;
import com.craft.java.lms.dto.LeadDTO;
import com.craft.java.lms.model.Lead;
import com.craft.java.lms.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private CmsClient cmsClient;

    // Create and save a new lead entity
    public Lead createLead(LeadDTO dto) {
        Lead lead = new Lead();
        lead.setFirstName(dto.getFirstName());
        lead.setLastName(dto.getLastName());
        lead.setEmail(dto.getEmail());
        lead.setPhone(dto.getPhone());
        lead.setCompany(dto.getCompany());
        lead.setSource(dto.getSource());
        lead.setCustomFields(dto.getCustomFields());
        lead.setConverted(false); // default not converted

        return leadRepository.save(lead);
    }

    // Create lead and return mapped response DTO
    public LeadDTO createLeadAndReturnResponse(LeadDTO dto) {
        Lead lead = createLead(dto);
        return mapToResponseDTO(lead);
    }

    // Map Lead entity to LeadDTO with leadId and status
    public LeadDTO mapToResponseDTO(Lead lead) {
        LeadDTO dto = new LeadDTO();
        dto.setLeadId("LEAD" + lead.getId());
        dto.setFirstName(lead.getFirstName());
        dto.setLastName(lead.getLastName());
        dto.setEmail(lead.getEmail());
        dto.setPhone(lead.getPhone());
        dto.setCompany(lead.getCompany());
        dto.setSource(lead.getSource());
        dto.setCustomFields(lead.getCustomFields());
        dto.setStatus(lead.isConverted() ? "Converted" : "Open");
        return dto;
    }

    // Convert an existing lead to customer by calling CMS and update lead status
    public boolean convertLead(Long leadId) {
        Optional<Lead> leadOpt = leadRepository.findById(leadId);
        if (leadOpt.isEmpty()) return false;

        Lead lead = leadOpt.get();

        Map<String, Object> customerPayload = new HashMap<>();
        customerPayload.put("firstName", lead.getFirstName());
        customerPayload.put("lastName", lead.getLastName());
        customerPayload.put("email", lead.getEmail());
        customerPayload.put("phone", lead.getPhone());
        customerPayload.put("company", lead.getCompany());
        customerPayload.put("customFields", lead.getCustomFields());

        try {
            boolean success = cmsClient.createCustomer(customerPayload);
            if (success) {
                lead.setConverted(true);
                leadRepository.save(lead);
            }
            return success;
        } catch (Exception e) {
            System.err.println("Error converting lead ID " + leadId + ": " + e.getMessage());
            return false;
        }
    }
}
