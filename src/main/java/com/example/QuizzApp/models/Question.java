package com.example.QuizzApp.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
