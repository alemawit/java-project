package com.craft.java.soms.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ShippingAddress {
    private String street;
    private String city;
    private String state;
    private String zipcode;
}

