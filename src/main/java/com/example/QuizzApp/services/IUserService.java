package com.example.QuizzApp.services;

import com.example.QuizzApp.dto.UserRegisterDTO;
import com.example.QuizzApp.exceptions.UserWithEmailAlreadyExistException;
import com.example.QuizzApp.exceptions.UserWithUsernameAlreadyExistException;
import com.example.QuizzApp.models.User;

public interface IUserService {
    User registerNewUserAccount(UserRegisterDTO userRegisterDTO) throws UserWithEmailAlreadyExistException, UserWithUsernameAlreadyExistException;
}
