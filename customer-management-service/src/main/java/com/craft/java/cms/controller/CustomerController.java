package com.craft.java.cms.controller;

import com.craft.java.cms.dto.CustomerResponseDTO;
import com.craft.java.cms.model.Customer;
import com.craft.java.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create customer
    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody Customer customer) {
        CustomerResponseDTO createdDTO = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdDTO);
    }


    // Update customer
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        CustomerResponseDTO updatedDTO = customerService.updateCustomer(id, updatedCustomer);
        return ResponseEntity.ok(updatedDTO);
    }

    // Get customer by id
    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerResponseDTO> get(@PathVariable Long id) {
        CustomerResponseDTO customerDTO = customerService.getCustomer(id);
        return ResponseEntity.ok(customerDTO);
    }
}
