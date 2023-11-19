package com.example.QuizzApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerDTO {
    @NotNull
    @NotEmpty
    @NotBlank(message = "Answer cannot be empty")
    private String name;
    private Boolean isCorrect;
}
