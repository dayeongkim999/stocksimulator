package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocksimul_game")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    // 어떤 유저의 게임인지 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer currentTurn; // 현재 진행 중인 턴 (예: 1, 2, 3...)

    @Column(nullable = false)
    private Long currentMoney; // 현재 유저의 남은 현금 잔고

    @Column(nullable = false)
    private Boolean isFinished; // 게임 종료 여부

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 데이터가 처음 DB에 저장될 때 자동으로 초기값 세팅
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.currentTurn == null) this.currentTurn = 1;
        if (this.isFinished == null) this.isFinished = false;
        if (this.currentMoney == null) this.currentMoney = 5000000L; // 초기 자본금 (예: 500만 원)
    }
}