package com.example.QuizzApp.controllers;

import com.example.QuizzApp.dto.UserRegisterDTO;
import com.example.QuizzApp.exceptions.UserWithEmailAlreadyExistException;
import com.example.QuizzApp.exceptions.UserWithUsernameAlreadyExistException;
import com.example.QuizzApp.models.User;
import com.example.QuizzApp.repositories.UserRepository;
import com.example.QuizzApp.services.UserService;
import com.example.QuizzApp.utils.UserRegisterValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    private final UserRepository userRepository;
    private final UserRegisterValidator userRegisterValidator;
    private final UserService userService;
    public RegisterController(UserRepository userRepository, UserRegisterValidator userRegisterValidator, UserService userService) {
        this.userRepository = userRepository;
        this.userRegisterValidator = userRegisterValidator;
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage(){
        ModelAndView registerPage = new ModelAndView("register");
        registerPage.addObject("user",new UserRegisterDTO());
        return registerPage;
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult,
                               HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        userRegisterValidator.validate(userRegisterDTO,bindingResult);
        try{
            User user = userService.registerNewUserAccount(userRegisterDTO);
            userRepository.save(user);
        }
        catch (UserWithEmailAlreadyExistException | UserWithUsernameAlreadyExistException ex1){
            bindingResult.rejectValue("email",ex1.getMessage());
        }
        if(bindingResult.hasErrors()){
            return "/register";
        }
        System.out.println(httpServletRequest.getSession().toString());
        return "redirect:/";
    }

}
