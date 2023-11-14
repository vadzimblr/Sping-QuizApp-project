package com.example.QuizzApp.services;

import com.example.QuizzApp.exceptions.AuthenticationServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    public AuthenticationService(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }
    public void AuthenticateUser(String username, String password,
                                 HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationServiceException {
        if(username == null){
            throw new AuthenticationServiceException("The Username field cannot be empty or null");
        } else if (password == null) {
            throw new AuthenticationServiceException("The Password field cannot be empty or null");
        }
        else {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
            Authentication authentication = authenticationManager.authenticate(token);
            if (authentication.isAuthenticated()) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
                securityContextRepository.saveContext(securityContext, request, response);
            }
        }

    }
}
