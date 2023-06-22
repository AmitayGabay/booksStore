package com.example.ShoppingWebsiteServer.service;

import com.example.ShoppingWebsiteServer.model.CustomUser;
import com.example.ShoppingWebsiteServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUser register(CustomUser user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getAddress() == null
        || user.getUsername() == null || user.getPassword() == null) {
            System.out.println("User not created, first name, last name, email, address, username and password are required");
            return null;
        }
        CustomUser userWithTheSameEmail = getUserByEmail(user.getEmail());
        CustomUser userWithTheSameUsername = getUserByUsername(user.getUsername());
        if(userWithTheSameEmail != null || userWithTheSameUsername != null){
            System.out.println("This email or username already exists in the system");
            return null;
        }
        return userRepository.register(user);
    }

    @Override
    public String deleteUser(String username) {
        CustomUser registeredUser = userRepository.getUserByUsername(username);
        if (registeredUser == null) {
            return "The user with this username does not exist, so it cannot be deleted";
        }
        return userRepository.deleteUser(registeredUser.getId());
    }

    @Override
    public CustomUser getUserById(Integer id) {
        if (id == null) {
            System.out.println("It is not possible to accept the user without id");
            return null;
        }
        return userRepository.getUserById(id);
    }

    @Override
    public CustomUser getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("It is not possible to accept the user without username");
            return null;
        }
        return userRepository.getUserByUsername(username);
    }

    @Override
    public CustomUser getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("It is not possible to accept the user without email");
            return null;
        }
        return userRepository.getUserByEmail(email);
    }

//    @Override
//    public List<CustomUser> getAllUsers() {
//        return userRepository.getAllUsers();
//    }
}
