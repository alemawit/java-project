package com.craft.java.cms.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Address.java
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
private String street;
private String city;
private String state;
private String zipcode;
}