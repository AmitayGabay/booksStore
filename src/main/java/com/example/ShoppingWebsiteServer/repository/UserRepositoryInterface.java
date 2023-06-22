package com.example.ShoppingWebsiteServer.repository;

import com.example.ShoppingWebsiteServer.model.CustomUser;

import java.util.List;

public interface UserRepositoryInterface {
    CustomUser register(CustomUser user);

    String deleteUser(Integer id);

    CustomUser getUserById(Integer id);

    CustomUser getUserByUsername(String username);

    CustomUser getUserByEmail(String email);
}
