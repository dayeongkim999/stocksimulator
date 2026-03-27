package com.example.demo.service;

import com.example.demo.dto.StageResponse;
import com.example.demo.entity.Stage;
import com.example.demo.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;

    // 1. 사이드 메뉴용 스테이지 목록 조회
    @Transactional(readOnly = true)
    public List<StageResponse> getStagesByType(String stageType) {
        return stageRepository.findByStageTypeOrderByStageNumberAsc(stageType).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 2. 글 읽기용 특정 스테이지 상세 조회
    @Transactional(readOnly = true)
    public StageResponse getStageByNumber(Integer stageNumber) {
        Stage stage = stageRepository.findByStageNumber(stageNumber)
                .orElseThrow(() -> new RuntimeException("스테이지를 찾을 수 없습니다."));
        return convertToDto(stage);
    }

    private StageResponse convertToDto(Stage stage) {
        return StageResponse.builder()
                .stageNumber(stage.getStageNumber())
                .title(stage.getTitle())
                .description(stage.getDescription())
                .content(stage.getContent())
                .stageType(stage.getStageType())
                .build();
    }
}