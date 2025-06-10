package com.craft.java.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.craft.java.cms.model.Customer;
import com.craft.java.cms.service.CustomerService;

import lombok.RequiredArgsConstructor;

//CustomerController.java
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

 private final CustomerService customerService;

 @PostMapping("/create")
 public ResponseEntity<Customer> create(@RequestBody Customer customer) {
     return ResponseEntity.ok(customerService.createCustomer(customer));
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
     return ResponseEntity.ok(customerService.updateCustomer(id, customer));
 }

 @GetMapping("/get/{id}")
 public ResponseEntity<Customer> get(@PathVariable Long id) {
     return ResponseEntity.ok(customerService.getCustomer(id));
 }


}

