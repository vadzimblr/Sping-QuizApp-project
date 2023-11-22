package com.example.QuizzApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class QuestionDTO {
    @NotNull
    @NotEmpty
    @NotBlank(message = "Question cannot be empty")
    private String name;
    private String description;
    @NotNull
    @NotEmpty
    private List<AnswerDTO> answers;
    // Геттеры и сеттеры
}

