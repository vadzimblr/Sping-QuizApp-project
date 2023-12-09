package com.example.QuizzApp.utils;

import com.example.QuizzApp.dto.ResultDTO.ResultQuizDTO;
import com.example.QuizzApp.repositories.QuestionRepository;
import com.example.QuizzApp.repositories.QuizRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class QuizResultValidator implements Validator {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    public QuizResultValidator(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ResultQuizDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ResultQuizDTO resultObject = (ResultQuizDTO) target;
        if(!quizRepository.existsByHash(resultObject.getQuizHash())){
            errors.rejectValue("QuizNotExists","[QuizNotExists]: Data not synchronized with the server");
        }
        for(var question: resultObject.getQuestions()){
            if(!questionRepository.existsByName(question.getName())){
                errors.rejectValue("QuestionNotExists","[QuestionNotExists]: Data not synchronized with the server");
                break;
            }
            else {
                if(question.getAnswers() == null){
                    errors.rejectValue("AnswersEmptyError","Answers can't be empty");
                }
            }
        }
    }
}
