package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.FavoriteRequest;
import com.example.ShoppingWebsiteServer.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements BookRepositoryInterface {
  @Autowired private JdbcTemplate jdbcTemplate;

  private static final String BOOKS_TABLE = "books";
  private static final String BOOK_TO_USER_TABLE = "book_to_user";

  @Override
  public Book addToFavorites(FavoriteRequest favoriteRequest, Book book) {
    try {
      String sql =
          String.format("INSERT INTO %s (book_id, user_id) VALUES (?,?)", BOOK_TO_USER_TABLE);
      jdbcTemplate.update(sql, favoriteRequest.getBookId(), favoriteRequest.getUserId());
      return book;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public String removeFromFavorites(FavoriteRequest favoriteRequest) {
    try {
      String sql =
          String.format("DELETE FROM %s WHERE book_id = ? AND user_id = ?", BOOK_TO_USER_TABLE);
      jdbcTemplate.update(sql, favoriteRequest.getBookId(), favoriteRequest.getUserId());
      return "The book hes been successfully removed from favorites";
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  @Override
  public List<Book> getFavorites(Integer id) {
    try {
      String sql =
          String.format(
              "SELECT books.id, books.title, books.author , books.description, books.usd_price, books.amount FROM %s \n"
                  + "INNER JOIN %s \n"
                  + "ON books.id = book_to_user.book_id \n"
                  + "WHERE book_to_user.user_id = ?",
              BOOKS_TABLE, BOOK_TO_USER_TABLE);
      List<Book> favorites = jdbcTemplate.query(sql, new BookMapper(), id);
      return favorites;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public List<Book> getFavoritesByName(List<Book> favorites, String name) {
    List<Book> results = new ArrayList<Book>();
    favorites.forEach(
        favorite -> {
          if (favorite.getTitle().contains(name.trim().toLowerCase())) results.add(favorite);
        });
    return results;
  }

  @Override
  public Boolean isFavoriteItem(FavoriteRequest favoriteRequest) {
    try {
      String sql =
          String.format(
              "SELECT books.id, books.title, books.author , books.description, books.usd_price, books.amount FROM %s \n"
                  + "INNER JOIN %s \n"
                  + "ON books.id = book_to_user.book_id \n"
                  + "WHERE book_to_user.book_id = ? AND book_to_user.user_id = ?",
              BOOKS_TABLE, BOOK_TO_USER_TABLE);
      Book item =
          jdbcTemplate.queryForObject(
              sql, new BookMapper(), favoriteRequest.getBookId(), favoriteRequest.getUserId());
      if (item == null) {
        return false;
      }
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  @Override
  public Book getBookById(Integer id) {
    try {
      String sql = String.format("SELECT * FROM %s WHERE id = ?", BOOKS_TABLE);
      Book book = jdbcTemplate.queryForObject(sql, new BookMapper(), id);
      return book;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public List<Book> getBooksByName(String name) {
    try {
      String sql = String.format("SELECT * FROM %s WHERE title LIKE ?", BOOKS_TABLE);
      String helper = "%" + name.trim().toLowerCase() + "%";
      List<Book> book = jdbcTemplate.query(sql, new BookMapper(), helper);
      return book;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public List<Book> getAllBooks() {
    try {
      String sql = String.format("SELECT * FROM %s", BOOKS_TABLE);
      List<Book> books = jdbcTemplate.query(sql, new BookMapper());
      return books;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
