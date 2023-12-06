package com.example.QuizzApp.controllers;

import com.example.QuizzApp.dto.QuizDTO;
import com.example.QuizzApp.models.User;
import com.example.QuizzApp.repositories.UserRepository;
import com.example.QuizzApp.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final QuizService quizService;
    private final UserRepository userRepository;
    public ApiController(QuizService quizService, UserRepository userRepository) {
        this.quizService = quizService;
        this.userRepository = userRepository;
    }

    @PostMapping("/createQuiz")
    public ResponseEntity<String> saveData(@Valid @RequestBody QuizDTO quizDTO, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError error: bindingResult.getFieldErrors()){
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
            Map<String,List> response = new HashMap<>();
            response.put("errors",errors);
            return ResponseEntity.badRequest().body(response.toString());
        }
        User user = userRepository.findByUsername(principal.getName());
        quizService.saveData(quizDTO,user);
        return ResponseEntity.ok("Data saved successfully");
    }
}