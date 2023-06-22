package com.example.ShoppingWebsiteServer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Integer id;
    private Integer userId;
    private LocalDate orderDate;
    private String shippingAddress;
    private Double totalPrice;
    private OrderStatus status;

    public Order(Integer userId, String shippingAddress) {
        this.userId = userId;
        this.orderDate = LocalDate.now();
        this.shippingAddress = shippingAddress;
    }

    public Order(Integer id, Integer userId, LocalDate orderDate, String shippingAddress, Double totalPrice, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.status = status;
    }
 }
