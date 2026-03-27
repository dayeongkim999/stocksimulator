package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.QuizProgress;
import com.example.demo.entity.User;
import com.example.demo.repository.QuizProgressRepository;
import com.example.demo.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizProgressRepository quizProgressRepository;

    @Transactional(readOnly = true)
    public List<QuizResponse> getQuizzesByStage(Integer stageNumber) {
        return quizRepository.findByStageNumber(stageNumber).stream()
                .map(quiz -> QuizResponse.builder()
                        .id(quiz.getId())
                        .stageNumber(quiz.getStageNumber())
                        .question(quiz.getQuestion())
                        .option1(quiz.getOption1())
                        .option2(quiz.getOption2())
                        .option3(quiz.getOption3())
                        .option4(quiz.getOption4())
                        .category(quiz.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuizResultResponse checkAnswer(Long quizId, Integer selectedAnswer) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("퀴즈를 찾을 수 없습니다"));

        boolean isCorrect = quiz.getCorrectAnswer().equals(selectedAnswer);

        return QuizResultResponse.builder()
                .isCorrect(isCorrect)
                .correctAnswer(quiz.getCorrectAnswer())
                .explanation(quiz.getExplanation())
                .build();
    }

    @Transactional
    public QuizProgressResponse saveProgress(User user, Integer stageNumber,
                                             Integer correctAnswers, Integer totalQuestions) {
        // 별점 계산 (예: 80% 이상 3점, 60% 이상 2점, 40% 이상 1점)
        double correctRate = (double) correctAnswers / totalQuestions;
        int stars;
        if (correctRate >= 0.8) {
            stars = 3;
        } else if (correctRate >= 0.6) {
            stars = 2;
        } else if (correctRate >= 0.4) {
            stars = 1;
        } else {
            stars = 0;
        }

        boolean isCleared = correctRate >= 0.6; // 60% 이상이면 클리어

        // 기존 기록이 있으면 업데이트, 없으면 새로 생성
        QuizProgress progress = quizProgressRepository
                .findByUserAndStageNumber(user, stageNumber)
                .orElse(QuizProgress.builder()
                        .user(user)
                        .stageNumber(stageNumber)
                        .build());

        // 더 좋은 기록일 경우에만 업데이트
        if (progress.getId() == null || stars > progress.getStars()) {
            progress.setIsCleared(isCleared);
            progress.setStars(stars);
            progress.setCorrectAnswers(correctAnswers);
            progress.setTotalQuestions(totalQuestions);
            progress = quizProgressRepository.save(progress);
        }

        return QuizProgressResponse.builder()
                .id(progress.getId())
                .stageNumber(progress.getStageNumber())
                .isCleared(progress.getIsCleared())
                .stars(progress.getStars())
                .correctAnswers(progress.getCorrectAnswers())
                .totalQuestions(progress.getTotalQuestions())
                .completedAt(progress.getCompletedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<QuizProgressResponse> getUserProgress(User user) {
        return quizProgressRepository.findByUserOrderByStageNumberAsc(user).stream()
                .map(progress -> QuizProgressResponse.builder()
                        .id(progress.getId())
                        .stageNumber(progress.getStageNumber())
                        .isCleared(progress.getIsCleared())
                        .stars(progress.getStars())
                        .correctAnswers(progress.getCorrectAnswers())
                        .totalQuestions(progress.getTotalQuestions())
                        .completedAt(progress.getCompletedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 한 번에 채점하고 진행도까지 저장하는 안전한 로직
    @Transactional
    public QuizSubmitResponse submitAndSaveProgress(User user, QuizSubmitRequest request) {
        int correctCount = 0;
        int totalCount = request.getAnswers().size();
        List<QuizSubmitResult> resultList = new ArrayList<>();

        // 1. 프론트가 보낸 답안을 하나씩 돌면서 서버가 직접 채점!
        for (QuizSubmitItem item : request.getAnswers()) {
            Quiz quiz = quizRepository.findById(item.getQuizId())
                    .orElseThrow(() -> new RuntimeException("퀴즈를 찾을 수 없습니다"));

            boolean isCorrect = quiz.getCorrectAnswer().equals(item.getSelectedAnswer());
            if (isCorrect) {
                correctCount++;
            }

            // 해설지 리스트에 추가
            resultList.add(QuizSubmitResult.builder()
                    .quizId(quiz.getId())
                    .isCorrect(isCorrect)
                    .correctAnswer(quiz.getCorrectAnswer())
                    .explanation(quiz.getExplanation())
                    .build());
        }

        // 2. 서버가 직접 별점 계산
        double correctRate = (double) correctCount / totalCount;
        int stars = (correctRate >= 0.8) ? 3 : (correctRate >= 0.6) ? 2 : (correctRate >= 0.4) ? 1 : 0;
        boolean isCleared = correctRate >= 0.6;

        // 3. 서버가 직접 DB에 진행도 저장 (기존 갱신 로직 활용)
        QuizProgress progress = quizProgressRepository
                .findByUserAndStageNumber(user, request.getStageNumber())
                .orElse(QuizProgress.builder()
                        .user(user)
                        .stageNumber(request.getStageNumber())
                        .build());

        if (progress.getId() == null || stars > progress.getStars()) {
            progress.setIsCleared(isCleared);
            progress.setStars(stars);
            progress.setCorrectAnswers(correctCount);
            progress.setTotalQuestions(totalCount);
            quizProgressRepository.save(progress);
        }

        // 4. 최종 성적표 반환
        return QuizSubmitResponse.builder()
                .correctAnswers(correctCount)
                .totalQuestions(totalCount)
                .stars(stars)
                .isCleared(isCleared)
                .results(resultList) // 결과창에서 볼 해설 리스트
                .build();
    }
}