package com.example.QuizzApp.exceptions;

public class UserWithEmailAlreadyExistException extends Exception{
    public UserWithEmailAlreadyExistException(String message) {
        super(message);
    }
}
