package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.Order;
import com.example.ShoppingWebsiteServer.model.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Order(rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getDate("order_date").toLocalDate(),
                rs.getString("shipping_address"),
                rs.getDouble("total_price"),
                OrderStatus.valueOf(rs.getString("status")));
    }
}
