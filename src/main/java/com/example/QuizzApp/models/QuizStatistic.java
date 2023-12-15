package com.example.QuizzApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
@Data
@Entity(name = "QuizStatistic")
public class QuizStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private double score;
    private int correctAnswers;
    private int incorrectAnswers;
    private int timeSpent;
}
