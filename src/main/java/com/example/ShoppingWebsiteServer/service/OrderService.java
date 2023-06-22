package com.example.ShoppingWebsiteServer.service;

import com.example.ShoppingWebsiteServer.model.*;
import com.example.ShoppingWebsiteServer.repository.BookRepository;
import com.example.ShoppingWebsiteServer.repository.OrderRepository;
import com.example.ShoppingWebsiteServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer createNewOrder(Order order) {
        return orderRepository.createNewOrder(order);
    }

    @Override
    public Book addItemToOrder(String username, Integer itemId) {
        if (itemId == null) {
            System.out.println("You cannot add an item to order without item id");
            return null;
        }
        if (username == null) {
            System.out.println("You cannot add an item to order without username");
            return null;
        }
    Book item = itemRepository.getBookById(itemId);
        if (item == null) {
            System.out.println("The item with this id does not exist in the system");
            return null;
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("The user with this username does not exist in the system");
            return null;
        }
        FavoriteRequest favoriteRequest = new FavoriteRequest(itemId, user.getId());
        Order openOrder = hasOpenOrder(favoriteRequest.getUserId());
        if (openOrder == null) {
            Order newOrder = new Order(favoriteRequest.getUserId(), user.getAddress());
            Integer orderId = createNewOrder(newOrder);
            if (orderId == -1) {
                System.out.println("The creation of the order failed, so an item cannot be added to the order");
                return null;
            }
            openOrder = getOrderById(username, orderId);
        }
        return orderRepository.addItemToOrder(item, openOrder);
    }

    @Override
    public String removeItemFromOrder(String username, OrderRequest orderRequest) {
        if (username == null) {
            return "You cannot remove an item from order without username";
        }
        if (orderRequest.getBookId() == null) {
            return "You cannot remove an item from order without item id";
        }
        if (orderRequest.getOrderId() == null) {
            return "You cannot remove an item from order without order id";
        }
    Book item = itemRepository.getBookById(orderRequest.getBookId());
        if (item == null) {
            return "The item with this id does not exist in the system";
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            return "The user with this username does not exist in the system";
        }
        Order order = orderRepository.getOrderById(orderRequest.getOrderId(), user.getId());
        if (order == null) {
            return "The order with this id does not exist in the system";
        }
        if (!isItemInTheOrder(orderRequest)) {
            return "The item is not in order and therefore cannot be removed from there";
        }
        if (order.getStatus() == OrderStatus.CLOSE) {
            return "It is not possible to remove an item from a closed order";
        }
        return orderRepository.removeItemFromOrder(item, order);
    }

    @Override
    public Order hasOpenOrder(Integer userId) {
        return orderRepository.hasOpenOrder(userId);
    }

    @Override
    public Boolean isItemInTheOrder(OrderRequest orderRequest) {
        return orderRepository.isItemInTheOrder(orderRequest);
    }

    @Override
    public Order getOrderById(String username, Integer orderId) {
        if (username == null) {
            System.out.println("You cannot update address in order without username");
            return null;
        }
        if (orderId == null) {
            System.out.println("The order cannot be accepted without order id");
            return null;
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("The user with this username does not exist in the system");
            return null;
        }
        return orderRepository.getOrderById(orderId, user.getId());
    }

    @Override
    public List<Order> getUserOrders(String username) {
        if (username == null) {
            System.out.println("You cannot get user orders without username");
            return null;
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("The user with this id does not exist in the system");
            return null;
        }
        return orderRepository.getUserOrders(user.getId());
    }

  @Override
  public List<Book> getOrderItems(String username, Integer id) {
        if (username == null) {
            System.out.println("You cannot get order items without username");
            return null;
        }
        if (id == null) {
            System.out.println("You cannot get order items without order id");
            return null;
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("The user with this id does not exist in the system");
            return null;
        }
        Order order = orderRepository.getOrderById(id, user.getId());
        if (order == null) {
            System.out.println("The order with this id does not exist in the system");
            return null;
        }
        if(!Objects.equals(order.getUserId(), user.getId())) {
            System.out.println("This order does not belong to the user, so he cannot receive its items");
            return null;
        }
        return orderRepository.getOrderBooks(id);
    }

    @Override
    public Order closeOrder(String username, Integer id) {
        if (username == null) {
            System.out.println("You cannot close the order without username");
            return null;
        }
        if (id == null) {
            System.out.println("You cannot close order without order id");
            return null;
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("The user with this id does not exist in the system");
            return null;
        }
        Order order = orderRepository.getOrderById(id, user.getId());
        if (order == null) {
            System.out.println("The order with this id does not exist in the system");
            return null;
        }
        return orderRepository.closeOrder(id, user.getId());
    }

}
