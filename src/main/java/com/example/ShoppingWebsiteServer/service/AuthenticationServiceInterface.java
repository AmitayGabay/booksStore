package com.example.ShoppingWebsiteServer.service;


import com.example.ShoppingWebsiteServer.security.model.AuthenticationRequest;
import com.example.ShoppingWebsiteServer.security.model.AuthenticationResponse;

public interface AuthenticationServiceInterface {
    AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception;
}
