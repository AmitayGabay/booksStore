package com.example.ShoppingWebsiteServer.service;

import com.example.ShoppingWebsiteServer.model.FavoriteRequest;
import com.example.ShoppingWebsiteServer.model.Book;

import java.util.List;

public interface BookServiceInterface {
    Book addToFavorites(String username, Integer itemId);

    String removeFromFavorites(String username, Integer itemId);

    List<Book> getFavorites(String username);

    List<Book> getFavoritesByName(String username, String name);

    Boolean isFavoriteItem(FavoriteRequest favoriteRequest);

    Book getItemById(Integer id);

    List<Book> getItemsByName(String name);

    List<Book> getAllItems();
}
