package com.example.ShoppingWebsiteServer.controller;

import com.example.ShoppingWebsiteServer.model.IdRequest;
import com.example.ShoppingWebsiteServer.model.Book;
import com.example.ShoppingWebsiteServer.service.BookService;
import com.example.ShoppingWebsiteServer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    @Autowired
    private JwtUtil jwtUtil;

  @Autowired private BookService bookService;

    @PostMapping(value = "/add-to-favorites", params = "Authorization")
    public Book addToFavorites(@RequestParam(value = "Authorization") String token, @RequestBody IdRequest idRequest) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        System.out.println(idRequest);
    return bookService.addToFavorites(username, idRequest.getId());
    }

    @DeleteMapping(value = "/remove-from-favorites", params = "Authorization")
    public String removeFromFavorites(@RequestParam(value = "Authorization") String token, @RequestBody IdRequest idRequest) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
    return bookService.removeFromFavorites(username, idRequest.getId());
    }

    @GetMapping(value = "/get-favorites", params = "Authorization")
    public List<Book> getFavorites(@RequestParam(value = "Authorization") String token) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
    return bookService.getFavorites(username);
    }

    @GetMapping(value = "/favorites", params = {"Authorization", "name"})
    public List<Book> getFavoritesByName(@RequestParam(value = "Authorization") String token, @RequestParam String name) {
        String jwtToken = token.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
    return bookService.getFavoritesByName(username, name);
    }

    @GetMapping(value = "search", params = "id")
    public Book getItemById(@RequestParam Integer id) {
    return bookService.getItemById(id);
    }

    @GetMapping(value = "search", params = "name")
    public List<Book> getItemsByName(@RequestParam String name) {
    return bookService.getItemsByName(name);
    }

    @GetMapping(value = "/all-books")
    public List<Book> getAllItems() {
    return bookService.getAllItems();
    }


}
