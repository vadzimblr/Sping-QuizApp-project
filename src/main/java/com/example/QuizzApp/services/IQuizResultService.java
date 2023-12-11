package com.example.QuizzApp.services;

import com.example.QuizzApp.dto.ResultDTO.ResultQuizDTO;

public interface IQuizResultService {
    void saveResults();
    void compareQuizResults(ResultQuizDTO resultObj);
}
