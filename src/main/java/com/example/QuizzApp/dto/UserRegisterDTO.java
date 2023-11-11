package com.example.QuizzApp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotNull
    @NotEmpty
    @NotBlank(message = "The username field cannot be empty!")
    @Size(min = 4, max = 48, message = "The username must be between 4 and 48 characters long")
    private String username;
    @NotEmpty
    @NotNull
    @NotBlank(message = "The password field cannot be empty!")
    @Size(min = 2, max = 255, message = "The password must be between 4 and 255 characters long")
    private String password;
    private String matchingPassword;
    @NotNull
    @NotEmpty
    @NotBlank(message = "The email field cannot be empty!")
    @Email
    private String email;

}
