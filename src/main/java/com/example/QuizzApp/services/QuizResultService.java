package com.example.QuizzApp.services;

import com.example.QuizzApp.dto.ResultDTO.ResultQuizDTO;
import com.example.QuizzApp.repositories.AnswerRepository;

public class QuizResultService implements IQuizResultService{
    private final AnswerRepository answerRepository;

    public QuizResultService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void saveResults() {

    }

    @Override
    public void compareQuizResults(ResultQuizDTO resultObj) {

    }
}
