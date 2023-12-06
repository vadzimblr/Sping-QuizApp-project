package com.example.QuizzApp.controllers;

import com.example.QuizzApp.dto.QuizDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account/{username}/addQuiz")
public class AccountController {

    @GetMapping
    public ModelAndView showAddQuizForm(@PathVariable String username){
        ModelAndView mv = new ModelAndView("createQuiz");
        mv.addObject("quiz",new QuizDTO());
        return mv;
    }

}
