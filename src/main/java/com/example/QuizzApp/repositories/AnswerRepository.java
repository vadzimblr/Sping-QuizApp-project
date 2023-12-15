package com.example.QuizzApp.repositories;

import com.example.QuizzApp.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query(value = "SELECT answers.name " +
            "FROM quizzes " +
            "JOIN questions ON quizzes.id = questions.quiz_id " +
            "JOIN answers ON questions.id = answers.question_id " +
            "WHERE quizzes.hash = :hash AND questions.name = :questionName AND answers.is_correct = true", nativeQuery = true)
    List<String> getCorrectAnswers(@Param("hash") String hash, @Param("questionName") String questionName);
    @Query(value = "SELECT answers.name " +
            "FROM quizzes " +
            "JOIN questions ON quizzes.id = questions.quiz_id " +
            "JOIN answers ON questions.id = answers.question_id " +
            "WHERE quizzes.hash = :hash", nativeQuery = true)
    List<String> getAllAnswers(@Param("hash") String hash);
    @Query(value = "SELECT answers.name " +
            "FROM quizzes " +
            "JOIN questions ON quizzes.id = questions.quiz_id " +
            "JOIN answers ON questions.id = answers.question_id " +
            "WHERE quizzes.hash = :hash AND answers.is_correct = true", nativeQuery = true)
    List<String> getAllCorrectAnswers(@Param("hash") String hash);
}
