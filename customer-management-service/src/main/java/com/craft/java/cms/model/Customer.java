package com.craft.java.cms.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Customer.java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String firstName;
 private String lastName;
 private String email;
 private String phone;

 @Embedded
 private Address address;

 private String notes;

 @Embedded
 private CustomFields customFields;
}



