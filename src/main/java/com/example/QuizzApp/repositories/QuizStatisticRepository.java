package com.example.QuizzApp.repositories;

import com.example.QuizzApp.models.QuizStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizStatisticRepository extends JpaRepository<QuizStatistic,Integer> {
    //Optional<QuizStatistic> findAllByQuizId(int id);
    List<QuizStatistic> findAllByQuizId(int id);


}
