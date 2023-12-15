package com.example.QuizzApp.repositories;

import com.example.QuizzApp.models.QuizStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizStatisticRepository extends JpaRepository<QuizStatistic,Integer> {
}
