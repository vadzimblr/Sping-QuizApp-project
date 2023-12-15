package com.example.QuizzApp.utils;

import com.example.QuizzApp.dto.AnswerDTO;
import com.example.QuizzApp.dto.QuestionDTO;
import com.example.QuizzApp.dto.QuizDTO;
import com.example.QuizzApp.dto.QuizStatisticDTO;
import com.example.QuizzApp.models.Answer;
import com.example.QuizzApp.models.Question;
import com.example.QuizzApp.models.Quiz;
import com.example.QuizzApp.models.QuizStatistic;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

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

        TypeMap<QuizStatistic, QuizStatisticDTO> quizStatisticMap = modelMapper.createTypeMap(QuizStatistic.class,QuizStatisticDTO.class);
        quizStatisticMap.addMappings(mapper->mapper.skip(QuizStatisticDTO::setUsername));
        quizStatisticMap.addMappings(mapper->mapper.skip(QuizStatisticDTO::setQuizName));
        quizStatisticMap.addMappings(mapper -> mapper.map(user -> user.getUser().getUsername(),QuizStatisticDTO::setUsername));
        quizStatisticMap.addMappings(mapper -> mapper.map(quiz -> quiz.getQuiz().getName(),QuizStatisticDTO::setQuizName));

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
    public QuizStatisticDTO quizStatisticToDto(QuizStatistic quizStatistic) {return modelMapper.map(quizStatistic, QuizStatisticDTO.class);}
    public List<QuizStatisticDTO> listQuizStatisticToDTO(List<QuizStatistic> quizStatistics) {
        Type listType = new TypeToken<List<QuizStatisticDTO>>() {}.getType();
        return modelMapper.map(quizStatistics, listType);
    }
}
