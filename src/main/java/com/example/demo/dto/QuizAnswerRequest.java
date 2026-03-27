package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizAnswerRequest {

    @NotNull(message = "퀴즈 ID는 필수입니다")
    private Long quizId;

    @NotNull(message = "답변은 필수입니다")
    @Min(value = 1, message = "답변은 1-4 사이여야 합니다")
    @Max(value = 4, message = "답변은 1-4 사이여야 합니다")
    private Integer selectedAnswer;
}