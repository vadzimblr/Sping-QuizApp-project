package com.example.QuizzApp.controllers;

import com.example.QuizzApp.dto.QuizDTO;
import com.example.QuizzApp.repositories.QuizRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/quiz/{hash}")
public class QuizController {
    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public ModelAndView showStartQuizPage(@PathVariable String hash){
        ModelAndView mv = new ModelAndView("startquiz");
        var quiz = quizRepository.findByHash(hash);
        mv.addObject("quiz", quiz.get());
        return mv;
    }
    @GetMapping("/preview")
    public String previewShow(){
        return "quiz";
    }
}
