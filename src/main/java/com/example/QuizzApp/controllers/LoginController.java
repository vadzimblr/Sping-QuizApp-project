package com.example.QuizzApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping("/login")
    public ModelAndView showLoginPage(){
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("title","Login form");
        return mv;
    }
}
