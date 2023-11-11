package com.example.QuizzApp.exceptions;

public class UserWithUsernameAlreadyExistException extends Exception{
    public UserWithUsernameAlreadyExistException(String message) {
        super(message);
    }
}
