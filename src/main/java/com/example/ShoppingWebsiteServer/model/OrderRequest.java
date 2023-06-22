package com.example.ShoppingWebsiteServer.model;
import lombok.Getter;

@Getter
public class OrderRequest {
    private Integer bookId;
    private Integer orderId;

    public OrderRequest(Integer bookId, Integer orderId) {
        this.bookId = bookId;
        this.orderId = orderId;
    }
}
