package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizSubmitResult {
    private Long quizId;
    private Boolean isCorrect;
    private Integer correctAnswer;
    private String explanation;
}