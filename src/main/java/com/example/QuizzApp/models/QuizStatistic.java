package com.example.QuizzApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
@Data
@Entity(name = "QuizStatistics")
public class QuizStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "quiz")
    private Quiz quiz;
    @ManyToOne
    @JoinColumn(name="user")
    private User user;
    private Timestamp started_at;
    private Timestamp finished_at;
    private int score;
    private int correctAnswers;
    private int incorrectAnswers;
    private int timeSpent;
}
