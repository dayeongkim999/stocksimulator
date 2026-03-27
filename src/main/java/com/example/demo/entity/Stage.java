package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer stageNumber; // 퀴즈와 연결될 스테이지 번호 (1, 2, 3...)

    @Column(nullable = false)
    private String title; // 스테이지명 (예: "금리의 기초")

    @Column(nullable = false)
    private String description; // 간략 소개 (사이드 메뉴에 뜰 내용)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 3~5분 분량의 읽을거리 (짧은 글 또는 신문 기사 본문)

    @Column(nullable = false)
    private String stageType; // "QUIZ"(짧은글) 또는 "NEWS"(신문기사)로 구분
}