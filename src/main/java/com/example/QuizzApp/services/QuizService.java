package com.example.QuizzApp.services;

import com.example.QuizzApp.dto.AnswerDTO;
import com.example.QuizzApp.dto.QuestionDTO;
import com.example.QuizzApp.dto.QuizDTO;
import com.example.QuizzApp.models.Answer;
import com.example.QuizzApp.models.Question;
import com.example.QuizzApp.models.Quiz;
import com.example.QuizzApp.models.User;
import com.example.QuizzApp.repositories.AnswerRepository;
import com.example.QuizzApp.repositories.QuestionRepository;
import com.example.QuizzApp.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizHashGeneratorService hashGenerator;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    public QuizService(QuizHashGeneratorService hashGenerator, AnswerRepository answerRepository, QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.hashGenerator = hashGenerator;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    public void saveData(QuizDTO quizDTO, User user){
        Quiz quiz = new Quiz();
        quiz.setName(quizDTO.getName());
        quiz.setIntroduction(quizDTO.getIntroduction());
        quiz.setHash(hashGenerator.generateHash());
        quiz.setThumbnail(quizDTO.getThumbnail());
        quiz.setUser(user);
        quiz.setDuration(quizDTO.getDuration());
        List<QuestionDTO> questionDTOList = quizDTO.getQuestions();
        quizRepository.save(quiz);
        for(QuestionDTO q: questionDTOList){
            Question question = new Question();
            question.setQuiz(quiz);
            question.setName(q.getName());
            question.setDescription(q.getDescription());
            questionRepository.save(question);
            List<AnswerDTO> answerDTOList = q.getAnswers();
            for(AnswerDTO a: answerDTOList ){
                Answer answer = new Answer();
                answer.setQuestion(question);
                answer.setName(a.getName());
                answer.setIsCorrect(a.getIsCorrect());
                answerRepository.save(answer);
            }
        }
    }
}
