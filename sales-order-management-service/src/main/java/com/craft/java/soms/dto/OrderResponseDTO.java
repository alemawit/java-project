package com.craft.java.soms.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponseDTO {
    private String orderId;
    private String customerId;
    private List<ProductDTO> products;
    private Double totalAmount;
    private String status;
    private Double discount;
    private ShippingAddressDTO shippingAddress;
    private String notes;
}
