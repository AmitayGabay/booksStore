package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderRepository implements OrderRepositoryInterface {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String ORDERS_TABLE = "orders";
  private static final String BOOKS_TABLE = "books";
  private static final String BOOK_TO_ORDER_TABLE = "book_to_order";

    @Override
    public Integer createNewOrder(Order order) {
        try {
            String sql = String.format("INSERT INTO %s (user_id, order_date, shipping_address) VALUES (?,?,?)", ORDERS_TABLE);
            jdbcTemplate.update(sql, order.getUserId(), order.getOrderDate(), order.getShippingAddress());
            Order openOrder = hasOpenOrder(order.getUserId());
            System.out.println("The order was created successfully");
            return openOrder.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1; // Order not created
        }
    }

  @Override
  public Book addItemToOrder(Book book, Order order) {
        try {
      String sql =
          String.format("INSERT INTO %s (book_id, order_id) VALUES (?,?)", BOOK_TO_ORDER_TABLE);
            jdbcTemplate.update(sql, book.getId(), order.getId());
            String additionalSql = String.format("UPDATE %s SET total_price = ? WHERE id = ?", ORDERS_TABLE);
            jdbcTemplate.update(additionalSql, order.getTotalPrice() + book.getUsdPrice(), order.getId());
            return book;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

  @Override
  public String removeItemFromOrder(Book book, Order order) {
        try {
      String sql =
          String.format(
              "DELETE FROM %s WHERE book_id = ? AND order_id = ? LIMIT 1", BOOK_TO_ORDER_TABLE);
            jdbcTemplate.update(sql, book.getId(), order.getId());
            String additionalSql = String.format("UPDATE %s SET total_price = ? WHERE id = ?", ORDERS_TABLE);
            jdbcTemplate.update(additionalSql, order.getTotalPrice() - book.getUsdPrice(), order.getId());
      String bookToOrderSql =
          String.format("SELECT * FROM %s WHERE order_id = ?", BOOK_TO_ORDER_TABLE);
            List<OrderRequest> bookToOrder = jdbcTemplate.query(bookToOrderSql, new OrderRequestMapper(), order.getId());
            if (bookToOrder.size() == 0) {
                String delOrderSql = String.format("DELETE FROM %s WHERE id = ?", ORDERS_TABLE);
                jdbcTemplate.update(delOrderSql, order.getId());
            }
            return "The book hes been successfully removed from the order";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public Order hasOpenOrder(Integer userId) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE user_id = ? AND status = ?", ORDERS_TABLE);
            Order openOrder = jdbcTemplate.queryForObject(sql, new OrderMapper(), userId, OrderStatus.TEMP.name());
            return openOrder;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean isItemInTheOrder(OrderRequest orderRequest) {
        try {
      String sql =
          String.format("SELECT * FROM %s WHERE book_id = ? AND order_id = ?", BOOK_TO_ORDER_TABLE);
      List<OrderRequest> bookToOrder =
          jdbcTemplate.query(
              sql, new OrderRequestMapper(), orderRequest.getBookId(), orderRequest.getOrderId());
            if (bookToOrder.size() == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Order getOrderById(Integer orderId, Integer userId) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ?", ORDERS_TABLE);
            Order order = jdbcTemplate.queryForObject(sql, new OrderMapper(), orderId);
            if (order != null && !Objects.equals(order.getUserId(), userId)) {
                System.out.println("This order does not belong to the user, so he cannot accept it");
                return null;
            }
            return order;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Order> getUserOrders(Integer id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE user_id = ?", ORDERS_TABLE);
            List<Order> orders = jdbcTemplate.query(sql, new OrderMapper(), id);
            return orders;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

  @Override
  public List<Book> getOrderBooks(Integer id) {
        try {
      String sql =
          String.format(
                  "SELECT books.id, books.title, books.author , books.description, books.usd_price, books.amount FROM %s \n"
                  + "INNER JOIN %s \n"
                  + "ON books.id = book_to_order.book_id \n"
                  + "WHERE book_to_order.order_id = ?",
              BOOKS_TABLE, BOOK_TO_ORDER_TABLE);
      List<Book> orderItems = jdbcTemplate.query(sql, new BookMapper(), id);
            return orderItems;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Order closeOrder(Integer id, Integer userId) {
        try {
      String Sql =
          String.format(
                  "SELECT books.id, books.title, books.author , books.description, books.usd_price, books.amount FROM %s \n"
                   + "INNER JOIN %s \n"
                  + "ON books.id = book_to_order.book_id \n"
                  + "WHERE book_to_order.order_id = ?",
              BOOKS_TABLE, BOOK_TO_ORDER_TABLE);
      List<Book> orderItems = jdbcTemplate.query(Sql, new BookMapper(), id);
            HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
      List<Book> unrepeatedItems = new ArrayList<Book>();
            orderItems.forEach(item -> {
                if (hashMap.get(item.getId()) != null) {
                    hashMap.put(item.getId(), hashMap.get(item.getId()) + 1);
                } else {
                    unrepeatedItems.add(item);
                    hashMap.put(item.getId(), 1);
                }
            });
      unrepeatedItems.forEach(
          item -> {
            if (item.getAmount() >= hashMap.get(item.getId())) {
              String additionalSql =
                  String.format(
                      "UPDATE %s SET status = ?, order_date = current_date WHERE id = ?",
                      ORDERS_TABLE);
              jdbcTemplate.update(additionalSql, OrderStatus.CLOSE.name(), id);
              String amountSql =
                  String.format("UPDATE %s SET amount = ? WHERE id = ?", BOOKS_TABLE);
              jdbcTemplate.update(
                  amountSql, item.getAmount() - hashMap.get(item.getId()), item.getId());
            } else {
              throw new CustomException(
                  "Error: The order cannot be placed, The amount of items in the order is greater than the amount of items in the store.");
            }
          });
            Order updatedOrder = getOrderById(id, userId);
            return updatedOrder;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
