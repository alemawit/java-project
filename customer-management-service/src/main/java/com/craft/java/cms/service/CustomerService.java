package com.craft.java.cms.service;

import org.springframework.stereotype.Service;

import com.craft.java.cms.model.Customer;
import com.craft.java.cms.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

//CustomerService.java
@Service
@RequiredArgsConstructor
public class CustomerService {

 private final CustomerRepository customerRepository;

 public Customer createCustomer(Customer customer) {
     return customerRepository.save(customer);
 }

 public Customer updateCustomer(Long id, Customer updatedCustomer) {
     Customer existing = customerRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Customer not found"));
     updatedCustomer.setId(id);
     return customerRepository.save(updatedCustomer);
 }

 public Customer getCustomer(Long id) {
     return customerRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Customer not found"));
 }
}

