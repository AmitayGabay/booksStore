package com.example.ShoppingWebsiteServer.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {
    private Integer bookId;
    private Integer userId;

    public FavoriteRequest(Integer itemId, Integer userId) {
        this.bookId = itemId;
        this.userId = userId;
    }
}
