package com.example.QuizzApp.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name ="answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "is_correct")
    private Boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
