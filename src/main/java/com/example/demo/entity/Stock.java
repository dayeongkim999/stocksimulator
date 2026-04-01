package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stocksimul_stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol; // 종목 코드 (예: AAPL, TSLA 등)

    @Column(nullable = false)
    private String companyName; // 회사명 (예: 애플, 테슬라)

    @Column(nullable = false)
    private Long basePrice; // 주식의 최초 상장가(초기 가격)

    @Column(nullable = false)
    private Double volatility; // 해당 주식의 변동성 (FastAPI의 gauss 계산에 쓰일 값)
}