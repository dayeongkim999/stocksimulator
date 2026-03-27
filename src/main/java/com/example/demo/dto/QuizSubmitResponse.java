package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class QuizSubmitResponse {
    private Integer correctAnswers;
    private Integer totalQuestions;
    private Integer stars;
    private Boolean isCleared;
    private List<QuizSubmitResult> results; // 개별 문제 해설지 모음
}