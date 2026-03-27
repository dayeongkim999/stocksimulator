package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.service.QuizService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;

    /**
     * 특정 스테이지의 퀴즈 목록 조회
     */
    @GetMapping("/stage/{stageNumber}")
    public ResponseEntity<List<QuizResponse>> getQuizzesByStage(@PathVariable Integer stageNumber) {
        return ResponseEntity.ok(quizService.getQuizzesByStage(stageNumber));
    }

    /**
     * 퀴즈 답안 체크
     */

//    @PostMapping("/check")
//    public ResponseEntity<QuizResultResponse> checkAnswer(@Valid @RequestBody QuizAnswerRequest request) {
//        return ResponseEntity.ok(quizService.checkAnswer(request.getQuizId(), request.getSelectedAnswer()));
//    }


    /**
     * 퀴즈 전체 답안 제출 및 자동 채점/저장
     */
    @PostMapping("/submit")
    public ResponseEntity<QuizSubmitResponse> submitQuiz(
            @RequestBody QuizSubmitRequest request,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(quizService.submitAndSaveProgress(user, request));
    }

    /**
     * 사용자 퀴즈 진행도 조회
     */
    @GetMapping("/progress")
    public ResponseEntity<List<QuizProgressResponse>> getUserProgress(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(quizService.getUserProgress(user));
    }
}