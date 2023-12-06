package com.example.QuizzApp.utils;

import com.example.QuizzApp.dto.AnswerDTO;
import com.example.QuizzApp.dto.QuestionDTO;
import com.example.QuizzApp.dto.QuizDTO;
import com.example.QuizzApp.models.Answer;
import com.example.QuizzApp.models.Question;
import com.example.QuizzApp.models.Quiz;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ModelToDtoMapper {
    private final ModelMapper modelMapper;

    public ModelToDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        TypeMap<Answer, AnswerDTO> answerMap = modelMapper.createTypeMap(Answer.class, AnswerDTO.class);

        TypeMap<Question, QuestionDTO> questionMap = modelMapper.createTypeMap(Question.class, QuestionDTO.class);
        questionMap.addMappings(mapper -> mapper.skip(QuestionDTO::setAnswers));
        questionMap.addMappings(mapper -> mapper.map(Question::getAnswers, QuestionDTO::setAnswers));

        TypeMap<Quiz, QuizDTO> quizMap = modelMapper.createTypeMap(Quiz.class, QuizDTO.class);
        quizMap.addMappings(mapper -> mapper.skip(QuizDTO::setQuestions));
        quizMap.addMappings(mapper -> mapper.map(Quiz::getQuestions, QuizDTO::setQuestions));
    }
    public AnswerDTO answerToAnswerDTO(Answer answer){
        return modelMapper.map(answer, AnswerDTO.class);
    }
    public QuestionDTO questionToQuestionDTO(Question question){
        return modelMapper.map(question, QuestionDTO.class);
    }
    public QuizDTO quizToQuizDTO(Quiz quiz){
        return modelMapper.map(quiz,QuizDTO.class);
    }

}
