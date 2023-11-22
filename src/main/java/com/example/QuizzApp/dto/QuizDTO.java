package com.example.QuizzApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    @NotNull
    @NotEmpty
    @NotBlank(message = "Quiz name cannot be empty")
    private String name;
    private String introduction;
    private int duration; //Not null
    private String thumbnail;
    private List<QuestionDTO> questions;
}