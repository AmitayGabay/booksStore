package com.example.ShoppingWebsiteServer.service;

import com.example.ShoppingWebsiteServer.model.FavoriteRequest;
import com.example.ShoppingWebsiteServer.model.Book;
import com.example.ShoppingWebsiteServer.model.CustomUser;
import com.example.ShoppingWebsiteServer.repository.BookRepository;
import com.example.ShoppingWebsiteServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements BookServiceInterface {

  @Autowired private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Book addToFavorites(String username, Integer bookId) {
        if (bookId == null) {
            System.out.println("You cannot add an book to favorites without book id");
            return null;
        }
        if (username == null) {
            System.out.println("You cannot add an book to favorites without username");
            return null;
        }
    Book book = bookRepository.getBookById(bookId);
        if (book == null) {
            System.out.println("The book with this id does not exist in the system");
            return null;
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("The user with this username does not exist in the system");
            return null;
        }
        FavoriteRequest favoriteRequest = new FavoriteRequest(bookId, user.getId());
        if (isFavoriteItem(favoriteRequest)) {
            System.out.println("The book is already in the favorites");
            return null;
        }
    return bookRepository.addToFavorites(favoriteRequest, book);
    }

    @Override
    public String removeFromFavorites(String username, Integer itemId) {
        if (itemId == null) {
            return "You cannot remove an item from favorites without item id";
        }
        if (username == null) {
            return "You cannot remove an item from favorites without username";
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            return "The user with this username does not exist in the system";
        }
        FavoriteRequest favoriteRequest = new FavoriteRequest(itemId, user.getId());
        if (!isFavoriteItem(favoriteRequest)) {
            return "The item is not in favorites and therefore cannot be removed from there";
        }
    return bookRepository.removeFromFavorites(favoriteRequest);
    }

    @Override
    public List<Book> getFavorites(String username) {
        if (username == null) {
            System.out.println("You cannot get favorite items without username");
            return null;
        }
        CustomUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            System.out.println("The user with this id does not exist in the system");
            return null;
        }
    return bookRepository.getFavorites(user.getId());
    }

    @Override
    public List<Book> getFavoritesByName(String username, String name) {
        List<Book> favorites = getFavorites(username);
        if (favorites == null || favorites.size() == 0) {
            System.out.println("This user has no favorite items");
            return null;
        }
        if (name == null || name.trim().isEmpty()) {
            System.out.println("name is null");
            return null;
        }
    return bookRepository.getFavoritesByName(favorites, name);
    }

    @Override
    public Boolean isFavoriteItem(FavoriteRequest favoriteRequest) {
    return bookRepository.isFavoriteItem(favoriteRequest);
    }

    @Override
    public Book getItemById(Integer id) {
        if (id == null) {
            System.out.println("It is not possible to accept the item without id");
            return null;
        }
    return bookRepository.getBookById(id);
    }

    @Override
    public List<Book> getItemsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("name is null");
            return null;
        }
    return bookRepository.getBooksByName(name);
    }

    @Override
    public List<Book> getAllItems() {
    return bookRepository.getAllBooks();
    }

}
