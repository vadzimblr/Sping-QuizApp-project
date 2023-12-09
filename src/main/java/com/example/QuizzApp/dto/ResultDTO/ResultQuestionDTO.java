package com.example.QuizzApp.dto.ResultDTO;

import lombok.Data;

import java.util.List;
@Data
public class ResultQuestionDTO {
    private String name;
    private List<ResultAnswerDTO> answers;
}
