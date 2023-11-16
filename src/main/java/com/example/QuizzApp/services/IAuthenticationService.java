package com.example.QuizzApp.services;

import com.example.QuizzApp.exceptions.AuthenticationServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthenticationService {
    void AuthenticateUser(String principal, String credentials,
                                 HttpServletRequest request, HttpServletResponse response) throws AuthenticationServiceException;
}
