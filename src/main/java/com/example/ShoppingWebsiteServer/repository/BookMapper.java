package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("description"),
                rs.getDouble("usd_price"),
                rs.getInt("amount"));
    }
}
