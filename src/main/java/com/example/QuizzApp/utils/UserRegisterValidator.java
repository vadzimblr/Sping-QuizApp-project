package com.example.QuizzApp.utils;

import com.example.QuizzApp.dto.UserRegisterDTO;
import com.example.QuizzApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserRegisterValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterDTO userRegisterDTO = (UserRegisterDTO) target;
        if(!userRegisterDTO.getMatchingPassword().equals(userRegisterDTO.getPassword())){
            errors.rejectValue("matchingPassword","Passwords don't match");
        }

    }
}
