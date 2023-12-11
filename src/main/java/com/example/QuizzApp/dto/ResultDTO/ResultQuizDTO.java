package com.example.QuizzApp.dto.ResultDTO;

import lombok.Data;

import java.util.List;
@Data
public class ResultQuizDTO {
    private String quizHash;
    private List<ResultQuestionDTO> questions;
}
