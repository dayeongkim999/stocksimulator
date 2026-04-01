package com.example.demo.dto.fastapi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class FastApiRequest {

    // 1-1. 턴 시작 요청 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TurnStart {
        private Long gameId;
        private Integer turnNum;
        private List<StockInfo> stocks;
        private NewsInfo news; // (선택) 이번 턴의 뉴스
        private EventInfo event; // (선택) 이번 턴의 이벤트
        private List<PlayerAction> playerActions;
        private Map<String, Object> marketSummary;
    }

    // 1-2. 턴 종료 요청 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TurnEnd {
        private Long gameId;
        private Integer turnNum;
        private List<PlayerAction> playerActions;
    }

    // --- 내부 부품 DTO들 ---

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class PlayerAction {
        private Long memberId;
        private String actionType; // "buy" or "sell"
        private String symbol;
        private Integer quantity;
        private Long price;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class StockInfo {
        private Long stockId;
        private String symbol;
        private Long basePrice;
        private Double volatility;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class NewsInfo {
        private Long newsId;
        private Map<String, Double> changeFactor;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class EventInfo {
        private Long eventId;
        private Map<String, Double> changeFactor;
    }
}