package com.example.ShoppingWebsiteServer.service;

import com.example.ShoppingWebsiteServer.model.*;

import java.util.List;

public interface OrderServiceInterface {
    Integer createNewOrder(Order order);

    Book addItemToOrder(String username, Integer itemId);

    String removeItemFromOrder(String username, OrderRequest orderRequest);

    Order hasOpenOrder(Integer userId);

    Boolean isItemInTheOrder(OrderRequest orderRequest);

    Order getOrderById(String username, Integer orderId);

    List<Order> getUserOrders(String username);

  List<Book> getOrderItems(String username, Integer id);

    Order closeOrder(String username, Integer id);
}
