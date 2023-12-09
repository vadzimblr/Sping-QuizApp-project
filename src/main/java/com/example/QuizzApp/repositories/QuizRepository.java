package com.example.QuizzApp.repositories;


import com.example.QuizzApp.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    Optional<Quiz> findByHash(String hash);
    boolean existsByHash(String hash);
}
