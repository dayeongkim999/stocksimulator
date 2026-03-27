package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizSubmitRequest {
    private Integer stageNumber;
    private List<QuizSubmitItem> answers; // 유저가 선택한 답안 리스트
}