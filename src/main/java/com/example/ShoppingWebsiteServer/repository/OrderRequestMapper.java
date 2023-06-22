package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.OrderRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRequestMapper implements RowMapper<OrderRequest> {
    @Override
    public OrderRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderRequest(rs.getInt("book_id"),
                rs.getInt("order_id"));
    }
}