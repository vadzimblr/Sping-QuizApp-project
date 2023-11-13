package com.example.QuizzApp.repositories;

import com.example.QuizzApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
