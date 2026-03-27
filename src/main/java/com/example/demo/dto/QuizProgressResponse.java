package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizProgressResponse {
    private Long id;
    private Integer stageNumber;
    private Boolean isCleared;
    private Integer stars;
    private Integer correctAnswers;
    private Integer totalQuestions;
    private LocalDateTime completedAt;
}