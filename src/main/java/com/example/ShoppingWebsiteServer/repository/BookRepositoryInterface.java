package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.FavoriteRequest;
import com.example.ShoppingWebsiteServer.model.Book;

import java.util.List;

public interface BookRepositoryInterface {
    Book addToFavorites(FavoriteRequest favoriteRequest, Book item);

    String removeFromFavorites(FavoriteRequest favoriteRequest);

    List<Book> getFavorites(Integer id);

    List<Book> getFavoritesByName(List<Book> favorites, String name);

    Boolean isFavoriteItem(FavoriteRequest favoriteRequest);

  Book getBookById(Integer id);

  List<Book> getBooksByName(String name);

  List<Book> getAllBooks();
}
