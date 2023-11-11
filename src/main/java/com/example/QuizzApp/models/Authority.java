package com.example.QuizzApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "authorities")
public class Authority {
    @Id
    private Long id;
    private String name;
}
