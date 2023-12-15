package com.example.QuizzApp.services;

import com.example.QuizzApp.dto.ResultDTO.ResultQuizDTO;
import com.example.QuizzApp.models.QuizStatistic;

import java.sql.Timestamp;

public interface IQuizResultService {
    void saveResults(ResultQuizDTO resultObj, Timestamp startAt, Timestamp endAt, String username);
    QuizStatistic compareQuizResults(ResultQuizDTO resultObj);
}
