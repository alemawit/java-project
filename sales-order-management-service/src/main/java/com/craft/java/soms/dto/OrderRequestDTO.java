package com.craft.java.soms.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {
    private String customerId;
    private List<ProductDTO> products;
    private Double discount;
    private ShippingAddressDTO shippingAddress;
    private String notes;
}
