package com.example.demo.dto;

import lombok.Data;

@Data
public class QuizSubmitItem {
    private Long quizId;
    private Integer selectedAnswer;
}