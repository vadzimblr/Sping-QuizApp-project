package com.example.QuizzApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password; //tmp min length
    private String email;
    private Boolean isconfirmed = true; //tmp true
    @ManyToOne
    @JoinColumn(name="authority")
    private Authority authority;

}
