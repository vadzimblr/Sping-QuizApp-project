package com.example.QuizzApp.repositories;

import com.example.QuizzApp.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
