package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.CustomUser;
import com.example.ShoppingWebsiteServer.model.OrderStatus;
import com.example.ShoppingWebsiteServer.model.RoleStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<CustomUser> {
    @Override
    public CustomUser mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new CustomUser(
        rs.getInt("id"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("email"),
        rs.getString("phone"),
        rs.getString("address"),
        rs.getString("username"),
        rs.getString("password")
//        RoleStatus.valueOf(rs.getString("role"))
    );
    }
}
