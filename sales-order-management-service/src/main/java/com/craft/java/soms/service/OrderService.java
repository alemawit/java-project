package com.craft.java.soms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craft.java.soms.dto.OrderRequestDTO;
import com.craft.java.soms.dto.OrderResponseDTO;
import com.craft.java.soms.model.Order;
import com.craft.java.soms.model.Product;
import com.craft.java.soms.model.ShippingAddress;
import com.craft.java.soms.repository.OrderRepository;
import com.craft.java.soms.client.CmsClient;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CmsClient cmsClient;

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        cmsClient.verifyCustomer(dto.getCustomerId());

        Order order = new Order();
        order.setCustomerId(dto.getCustomerId());
        order.setDiscount(dto.getDiscount());
        order.setNotes(dto.getNotes());
        order.setStatus("Pending");

        ShippingAddress address = new ShippingAddress();
        address.setStreet(dto.getShippingAddress().getStreet());
        address.setCity(dto.getShippingAddress().getCity());
        address.setState(dto.getShippingAddress().getState());
        address.setZipcode(dto.getShippingAddress().getZipcode());
        order.setShippingAddress(address);

        List<Product> products = dto.getProducts().stream().map(p -> {
            Product product = new Product();
            product.setProductId(p.getProductId());
            product.setQuantity(p.getQuantity());
            product.setPrice(p.getPrice());
            product.setOrder(order);
            return product;
        }).collect(Collectors.toList());

        order.setProducts(products);

        double totalAmount = products.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
        totalAmount -= dto.getDiscount();
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId("ORDER" + savedOrder.getId());
        response.setCustomerId(savedOrder.getCustomerId());
        response.setProducts(dto.getProducts());
        response.setTotalAmount(savedOrder.getTotalAmount());
        response.setStatus(savedOrder.getStatus());
        response.setDiscount(savedOrder.getDiscount());
        response.setShippingAddress(dto.getShippingAddress());
        response.setNotes(savedOrder.getNotes());

        return response;
    }
    public OrderResponseDTO updateOrder(Long orderId, OrderResponseDTO dto) {
        Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Update fields (you can adjust as needed)
        existingOrder.setDiscount(dto.getDiscount());
        existingOrder.setNotes(dto.getNotes());
        existingOrder.setStatus(dto.getStatus());

        ShippingAddress address = new ShippingAddress();
        address.setStreet(dto.getShippingAddress().getStreet());
        address.setCity(dto.getShippingAddress().getCity());
        address.setState(dto.getShippingAddress().getState());
        address.setZipcode(dto.getShippingAddress().getZipcode());
        existingOrder.setShippingAddress(address);

        // Optionally update products (skipped here for simplicity)
        // existingOrder.setProducts(...);

        // Recalculate total amount if needed
        // (You might want to use `dto.getProducts()` to recalculate it)
        existingOrder.setTotalAmount(dto.getTotalAmount());

        Order updatedOrder = orderRepository.save(existingOrder);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId("ORDER" + updatedOrder.getId());
        response.setCustomerId(updatedOrder.getCustomerId());
        response.setProducts(dto.getProducts()); // may need conversion
        response.setTotalAmount(updatedOrder.getTotalAmount());
        response.setStatus(updatedOrder.getStatus());
        response.setDiscount(updatedOrder.getDiscount());
        response.setShippingAddress(dto.getShippingAddress());
        response.setNotes(updatedOrder.getNotes());

        return response;
    }
    public OrderResponseDTO getOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

    OrderResponseDTO dto = new OrderResponseDTO();
    dto.setOrderId("ORDER" + order.getId());
    dto.setCustomerId(order.getCustomerId());
    dto.setDiscount(order.getDiscount());
    dto.setNotes(order.getNotes());
    dto.setStatus(order.getStatus());
    dto.setTotalAmount(order.getTotalAmount());

    ShippingAddress address = order.getShippingAddress();
    if (address != null) {
        dto.setShippingAddress(new com.craft.java.soms.dto.ShippingAddressDTO(
            address.getStreet(), address.getCity(), address.getState(), address.getZipcode()
        ));
    }

    dto.setProducts(order.getProducts().stream().map(p -> {
        var productDto = new com.craft.java.soms.dto.ProductDTO();
        productDto.setProductId(p.getProductId());
        productDto.setQuantity(p.getQuantity());
        productDto.setPrice(p.getPrice());
        return productDto;
    }).collect(Collectors.toList()));

    return dto;
}



}