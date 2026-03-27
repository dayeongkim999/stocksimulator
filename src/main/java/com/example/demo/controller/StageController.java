package com.example.demo.controller;

import com.example.demo.dto.StageResponse;
import com.example.demo.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    // 예: GET /api/stages?type=QUIZ  (짧은 글 퀴즈 목록)
    // 예: GET /api/stages?type=NEWS  (신문 기사 목록)
    @GetMapping
    public ResponseEntity<List<StageResponse>> getStagesByType(@RequestParam String type) {
        return ResponseEntity.ok(stageService.getStagesByType(type));
    }

    // 예: GET /api/stages/1 (1번 스테이지의 글과 소개글 불러오기)
    @GetMapping("/{stageNumber}")
    public ResponseEntity<StageResponse> getStageDetail(@PathVariable Integer stageNumber) {
        return ResponseEntity.ok(stageService.getStageByNumber(stageNumber));
    }
}