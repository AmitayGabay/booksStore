package com.example.ShoppingWebsiteServer.controller;

import com.example.ShoppingWebsiteServer.model.*;
import com.example.ShoppingWebsiteServer.service.OrderService;
import com.example.ShoppingWebsiteServer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "add-book-to-order", params = "Authorization")
    public Book addItemToOrder( @RequestParam(value = "Authorization") String token, @RequestBody IdRequest idRequest) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        return orderService.addItemToOrder(username, idRequest.getId());
    }

    @DeleteMapping(value = "remove-book-from-order", params = "Authorization")
    public String removeItemFromOrder(@RequestParam(value = "Authorization") String token, @RequestBody OrderRequest orderRequest) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        return orderService.removeItemFromOrder(username, orderRequest);
    }


    @GetMapping(params = {"Authorization", "orderid"})
    public Order getOrderById(@RequestParam(value = "Authorization") String token, @RequestParam(value = "orderid") Integer orderId) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        return orderService.getOrderById(username, orderId);
    }

    @GetMapping(value = "/user-orders", params = "Authorization")
    public List<Order> getUserOrders(@RequestParam(value = "Authorization") String token) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        return orderService.getUserOrders(username);
    }

  @GetMapping(
      value = "/order-books",
      params = {"Authorization", "id"})
  public List<Book> getOrderItems(
      @RequestParam(value = "Authorization") String token, @RequestParam Integer id) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        return orderService.getOrderItems(username, id);
    }

    @PutMapping(value = "/close", params = {"Authorization", "id"})
    public Order closeOrder(@RequestParam(value = "Authorization") String token, @RequestParam Integer id) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        return orderService.closeOrder(username, id);
    }

}
