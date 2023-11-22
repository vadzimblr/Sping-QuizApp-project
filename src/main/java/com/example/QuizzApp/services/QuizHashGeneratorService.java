package com.example.QuizzApp.services;

import com.example.QuizzApp.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

@Service
public class QuizHashGeneratorService {
    private final QuizRepository quizRepository;

    public QuizHashGeneratorService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }
    public String generateHash() {
        String randomString = generateRandomString();
        byte[] hashBytes = sha256(randomString.getBytes());
        String resultHash = Base64.getEncoder().encodeToString(hashBytes);
        resultHash = resultHash.substring(0, 6).toUpperCase();

        while (!checkUnique(resultHash)) {
            randomString = generateRandomString();
            hashBytes = sha256(randomString.getBytes());
            resultHash = Base64.getEncoder().encodeToString(hashBytes);
            resultHash = resultHash.substring(0, 6).toUpperCase();
        }

        return resultHash;
    }
    private Boolean checkUnique(String generatedHash){
        return quizRepository.findByHash(generatedHash).isEmpty();
    }
    private String generateRandomString(){
        SecureRandom random = new SecureRandom();
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }
}
