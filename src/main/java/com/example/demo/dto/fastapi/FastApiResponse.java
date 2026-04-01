package com.example.demo.dto.fastapi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class FastApiResponse {

    // 2-1. 턴 시작 응답 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TurnStart {
        private Long gameId;
        private Integer turnNum;
        private List<StockChange> stockChanges;
        private MarketSummary marketSummary;
        private String calculationTimestamp;
    }

    // 2-2. 턴 종료 응답 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TurnEnd {
        private Long gameId;
        private Integer turnNum;
        private String summary;
        private List<PlayerMoney> playerMoney;
        private String calculationTimestamp;
    }

    // --- 내부 부품 DTO들 ---

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class StockChange {
        private Long stockId;
        private String symbol;
        private Long previousPrice;
        private Long newPrice;
        private Long changeAmount;
        private Double changePercent;
        private String reason;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MarketSummary {
        private String overallTrend;
        private Double averageChange;
        private Double volatilityIndex;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class PlayerMoney {
        private Long gameId;
        private Integer turnNum;
        private Long memberId;
        private Long totalMoney;
        private Long profitLoss; // FastAPI가 계산해준 이번 턴의 순수 증감액
    }
}