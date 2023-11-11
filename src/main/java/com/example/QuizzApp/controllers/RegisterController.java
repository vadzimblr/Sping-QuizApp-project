package com.example.QuizzApp.controllers;

import com.example.QuizzApp.dto.UserRegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public ModelAndView showRegisterPage(){
        ModelAndView registerPage = new ModelAndView("register");
        registerPage.addObject("user",new UserRegisterDTO());
        return registerPage;
    }
    @PostMapping("/register")
    public String registerUser(){
        return "redirect:/";
    }
}
