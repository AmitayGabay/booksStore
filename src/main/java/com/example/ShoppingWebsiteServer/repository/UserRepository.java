package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.Order;
import com.example.ShoppingWebsiteServer.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements UserRepositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String USERS_TABLE = "users";
    private static final String ORDERS_TABLE = "orders";
  private static final String BOOK_TO_USER_TABLE = "book_to_user";

  private static final String BOOK_TO_ORDER_TABLE = "book_to_order";

    @Override
    public CustomUser register(CustomUser user) {
        try {
            String sql = String.format("INSERT INTO %s (first_name, last_name, email, phone, address, username, password) VALUES (?,?,?,?,?,?,?)", USERS_TABLE);
            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getUsername(), user.getPassword());
            CustomUser registeredUser = getUserByUsername(user.getUsername());
            registeredUser.setPassword("******");
            return registeredUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String deleteUser(Integer id) {
        try {
      String favoriteSql = String.format("DELETE FROM %s WHERE user_id = ?", BOOK_TO_USER_TABLE);
            jdbcTemplate.update(favoriteSql, id);
            String getOrderSql = String.format("SELECT * FROM %s WHERE user_id = ?", ORDERS_TABLE);
            List<Order> orders = jdbcTemplate.query(getOrderSql, new OrderMapper(), id);
      orders.forEach(
          order -> {
            String itemToOrderSql =
                String.format("DELETE FROM %s WHERE order_id = ?", BOOK_TO_ORDER_TABLE);
            jdbcTemplate.update(itemToOrderSql, order.getId());
          });
            String delOrderSql = String.format("DELETE FROM %s WHERE user_id = ?", ORDERS_TABLE);
            jdbcTemplate.update(delOrderSql, id);
            String sql = String.format("DELETE FROM %s WHERE id = ?", USERS_TABLE);
            jdbcTemplate.update(sql, id);
            return "User deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public CustomUser getUserById(Integer id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ?", USERS_TABLE);
            CustomUser user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
            user.setPassword("******");
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CustomUser getUserByUsername(String username) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE username = ?", USERS_TABLE);
            CustomUser user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CustomUser getUserByEmail(String email) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE email = ?", USERS_TABLE);
            CustomUser user = jdbcTemplate.queryForObject(sql, new UserMapper(), email);
            user.setPassword("******");
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
