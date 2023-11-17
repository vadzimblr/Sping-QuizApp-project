package com.example.QuizzApp.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String introduction;
    private String thumbnail;
    private String hash;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
