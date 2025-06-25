package com.craft.java.cms.controller;

import com.craft.java.cms.dto.CustomerResponseDTO;
import com.craft.java.cms.model.Customer;
import com.craft.java.cms.repository.CustomerRepository;
import com.craft.java.cms.service.CustomerService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
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

    @GetMapping("/get/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerByCustomerId(@PathVariable String customerId) {
        Optional<Customer> customerOptional = customerService.findCustomerByCustomerId(customerId);

        if (customerOptional.isPresent()) {
            CustomerResponseDTO dto = customerService.mapToCustomerResponseDTO(customerOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
