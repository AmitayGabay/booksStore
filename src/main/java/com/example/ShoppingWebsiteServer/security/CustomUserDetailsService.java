package com.example.ShoppingWebsiteServer.security;

import com.example.ShoppingWebsiteServer.service.UserService;
import com.example.ShoppingWebsiteServer.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userService.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("The user with this username does not exist");
        }
        return User.withDefaultPasswordEncoder().username(user.getUsername()).password(user.getPassword()).roles().build();
    }
}


