package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.Book;
import com.example.ShoppingWebsiteServer.model.Order;
import com.example.ShoppingWebsiteServer.model.OrderRequest;

import java.util.List;

public interface OrderRepositoryInterface {
    Integer createNewOrder(Order order);

    Book addItemToOrder(Book item, Order order);

    String removeItemFromOrder(Book item, Order order);

    Order hasOpenOrder(Integer userId);

    Boolean isItemInTheOrder(OrderRequest orderRequest);

    Order getOrderById(Integer orderId, Integer userId);

    List<Order> getUserOrders(Integer id);

    List<Book> getOrderBooks(Integer id);

    Order closeOrder(Integer id, Integer userId);
}
