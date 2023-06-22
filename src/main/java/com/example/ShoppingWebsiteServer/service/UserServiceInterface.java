package com.example.ShoppingWebsiteServer.service;

import com.example.ShoppingWebsiteServer.model.CustomUser;

import java.util.List;

public interface UserServiceInterface {
    CustomUser register(CustomUser user);

    String deleteUser(String username);

    CustomUser getUserById(Integer id);

    CustomUser getUserByUsername(String username);

    CustomUser getUserByEmail(String email);

//    List<CustomUser> getAllUsers();
}
