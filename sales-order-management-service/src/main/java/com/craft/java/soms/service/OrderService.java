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
}