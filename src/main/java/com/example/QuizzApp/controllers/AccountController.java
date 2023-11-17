package com.example.QuizzApp.controllers;

import com.example.QuizzApp.dto.QuizDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account/{username}")
public class AccountController {
    @GetMapping("/addQuiz")
    public ModelAndView showAddQuizzForm(@PathVariable String username){
        ModelAndView mv = new ModelAndView("createQuizz");
        mv.addObject("quiz",new QuizDTO());

        return mv;
    }

}
