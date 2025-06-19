package com.craft.java.soms.dto;

import lombok.Data;

@Data
public class ShippingAddressDTO {
    private String street;
    private String city;
    private String state;
    private String zipcode;

    public ShippingAddressDTO(String street, String city, String state, String zipcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }
}
