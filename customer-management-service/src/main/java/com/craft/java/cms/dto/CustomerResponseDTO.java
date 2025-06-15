package com.craft.java.cms.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDTO address;
    private String notes;
    private CustomFieldsDTO customFields;

    @Data
    public static class AddressDTO {
        private String street;
        private String city;
        private String state;
        private String zipcode;
    }

    @Data
    public static class CustomFieldsDTO {
        private String birthday;
        private String company;
    }
}
