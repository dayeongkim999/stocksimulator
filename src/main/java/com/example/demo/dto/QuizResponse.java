package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private Long id;
    private Integer stageNumber;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String category;
    // correctAnswer와 explanation은 퀴즈 제출 후에만 반환
}