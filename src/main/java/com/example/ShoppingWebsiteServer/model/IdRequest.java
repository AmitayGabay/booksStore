package com.example.ShoppingWebsiteServer.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdRequest {
    private Integer id;
    private String name;

    public IdRequest(Integer id, String name) {
        this.id = id;
        this.name = "bookId";
    }
}
