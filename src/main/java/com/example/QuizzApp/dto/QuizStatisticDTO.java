package com.example.QuizzApp.dto;

import com.example.QuizzApp.models.User;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class QuizStatisticDTO {
    private String quizName;
    private String username;
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private double score;
    private int correctAnswers;
    private int incorrectAnswers;
    private int timeSpent;
}
