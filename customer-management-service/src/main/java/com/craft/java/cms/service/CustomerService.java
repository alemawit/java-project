package com.craft.java.cms.service;

import org.springframework.stereotype.Service;

import com.craft.java.cms.dto.CustomerResponseDTO;
import com.craft.java.cms.dto.CustomerResponseDTO.AddressDTO;
import com.craft.java.cms.dto.CustomerResponseDTO.CustomFieldsDTO;
import com.craft.java.cms.model.Customer;
import com.craft.java.cms.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDTO createCustomer(Customer customer) {
        String generatedCode = "CUST" + System.currentTimeMillis();
        customer.setCustomerCode(generatedCode);
        Customer savedCustomer = customerRepository.save(customer);
        return mapToCustomerResponseDTO(savedCustomer);
    }


    public CustomerResponseDTO updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Update simple fields
        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setNotes(updatedCustomer.getNotes());

        // Update Address
        if (updatedCustomer.getAddress() != null) {
            if (existing.getAddress() == null) {
                existing.setAddress(updatedCustomer.getAddress());
            } else {
                existing.getAddress().setStreet(updatedCustomer.getAddress().getStreet());
                existing.getAddress().setCity(updatedCustomer.getAddress().getCity());
                existing.getAddress().setState(updatedCustomer.getAddress().getState());
                existing.getAddress().setZipcode(updatedCustomer.getAddress().getZipcode());
            }
        }

        // Update CustomFields
        if (updatedCustomer.getCustomFields() != null) {
            if (existing.getCustomFields() == null) {
                existing.setCustomFields(updatedCustomer.getCustomFields());
            } else {
                existing.getCustomFields().setBirthday(updatedCustomer.getCustomFields().getBirthday());
                existing.getCustomFields().setCompany(updatedCustomer.getCustomFields().getCompany());
            }
        }

        Customer savedCustomer = customerRepository.save(existing);
        return mapToCustomerResponseDTO(savedCustomer);
    }

    public CustomerResponseDTO getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return mapToCustomerResponseDTO(customer);
    }

    private CustomerResponseDTO mapToCustomerResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();

        // Set customerId properly
        dto.setCustomerId("CUST" + customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setNotes(customer.getNotes());

        // Map Address
        if (customer.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(customer.getAddress().getStreet());
            addressDTO.setCity(customer.getAddress().getCity());
            addressDTO.setState(customer.getAddress().getState());
            addressDTO.setZipcode(customer.getAddress().getZipcode());
            dto.setAddress(addressDTO);
        }

        // Map CustomFields
        if (customer.getCustomFields() != null) {
            CustomFieldsDTO customFieldsDTO = new CustomFieldsDTO();
            customFieldsDTO.setBirthday(customer.getCustomFields().getBirthday());
            customFieldsDTO.setCompany(customer.getCustomFields().getCompany());
            dto.setCustomFields(customFieldsDTO);
        }

        return dto;
    }
}
