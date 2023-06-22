package com.example.ShoppingWebsiteServer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUser {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String password;
//    private RoleStatus role;

    public CustomUser(Integer id, String firstName, String lastName, String email, String phone, String address, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.username = username;
        this.password = password;
//        this.role = role;
    }
 }
