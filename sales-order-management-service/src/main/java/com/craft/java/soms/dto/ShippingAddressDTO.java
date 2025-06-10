package com.craft.java.soms.dto;

import lombok.Data;

@Data
public class ShippingAddressDTO {
    private String street;
    private String city;
    private String state;
    private String zipcode;
}
