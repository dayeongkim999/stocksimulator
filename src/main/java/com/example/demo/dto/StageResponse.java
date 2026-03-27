package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StageResponse {
    private Integer stageNumber;
    private String title;
    private String description;
    private String content;
    private String stageType;
}