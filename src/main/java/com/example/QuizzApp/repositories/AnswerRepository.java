package com.example.QuizzApp.repositories;

import com.example.QuizzApp.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}
