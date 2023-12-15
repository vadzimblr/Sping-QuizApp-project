package com.example.QuizzApp.services;

import com.example.QuizzApp.dto.ResultDTO.ResultAnswerDTO;
import com.example.QuizzApp.dto.ResultDTO.ResultQuestionDTO;
import com.example.QuizzApp.dto.ResultDTO.ResultQuizDTO;
import com.example.QuizzApp.models.QuizStatistic;
import com.example.QuizzApp.repositories.AnswerRepository;
import com.example.QuizzApp.repositories.QuizRepository;
import com.example.QuizzApp.repositories.QuizStatisticRepository;
import com.example.QuizzApp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
@Service
public class QuizResultService implements IQuizResultService{
    private final AnswerRepository answerRepository;
    private final QuizStatisticRepository quizStatisticRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizResultService(AnswerRepository answerRepository, QuizStatisticRepository quizStatisticRepository, QuizRepository quizRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.quizStatisticRepository = quizStatisticRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveResults(ResultQuizDTO resultObj, Timestamp startAt, Timestamp endAt, String username) {
        if(resultObj==null || startAt == null || endAt == null)
            throw new NullPointerException();
        var quizStatistic = compareQuizResults(resultObj);
        var quiz = quizRepository.findByHash(resultObj.getQuizHash()).get();
        var user = userRepository.findByUsername(username);
        quizStatistic.setStartedAt(startAt);
        quizStatistic.setFinishedAt(endAt);
        quizStatistic.setTimeSpent(endAt.toLocalDateTime().getSecond()-startAt.toLocalDateTime().getSecond());
        quizStatistic.setQuiz(quiz);
        quizStatistic.setUser(user);

        quizStatisticRepository.save(quizStatistic);
    }

    @Override
    public QuizStatistic compareQuizResults(ResultQuizDTO resultObj) {
        var quizStatistic = new QuizStatistic();
        int correctAnswersCounter = 0;
        List<ResultQuestionDTO> questions = resultObj.getQuestions();

        for (var question : questions) {
            List<String> correctAnswers = answerRepository.getCorrectAnswers(resultObj.getQuizHash(), question.getName());
            List<ResultAnswerDTO> providedAnswers = question.getAnswers();

            for (ResultAnswerDTO providedAnswer : providedAnswers) {
                if (correctAnswers.contains(providedAnswer.getName())) {
                    correctAnswersCounter++;
                }
            }
        }
        int allCorrectAnswers = answerRepository.getAllCorrectAnswers(resultObj.getQuizHash()).size();

        int inCorrectAnswers = allCorrectAnswers-correctAnswersCounter;
        double score = (double) correctAnswersCounter /allCorrectAnswers;
        quizStatistic.setScore(Math.abs(score*100));
        quizStatistic.setIncorrectAnswers(inCorrectAnswers);
        quizStatistic.setCorrectAnswers(correctAnswersCounter);
        return quizStatistic;

    }
}
