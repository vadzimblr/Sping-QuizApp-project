package com.example.QuizzApp.services;

import com.example.QuizzApp.dto.UserRegisterDTO;
import com.example.QuizzApp.exceptions.UserWithEmailAlreadyExistException;
import com.example.QuizzApp.exceptions.UserWithUsernameAlreadyExistException;
import com.example.QuizzApp.models.User;
import com.example.QuizzApp.repositories.AuthorityRepository;
import com.example.QuizzApp.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public User registerNewUserAccount(UserRegisterDTO userRegisterDTO) throws UserWithEmailAlreadyExistException,
                                                                            UserWithUsernameAlreadyExistException
    {
        if(userRepository.existsByEmail(userRegisterDTO.getEmail())){
            throw new UserWithEmailAlreadyExistException("There is an account with that email address!");
        } else if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new UserWithUsernameAlreadyExistException("There is an account with that username address!");
        } else{
            User user = new User();
            user.setUsername(userRegisterDTO.getUsername());
            user.setEmail(userRegisterDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
            user.setAuthority(authorityRepository.findByName("ROLE_USER").get());
            user.setIsconfirmed(true); // tmp
            return user;
        }
    }

}
