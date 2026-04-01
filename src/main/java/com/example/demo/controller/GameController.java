package com.example.demo.controller;

import com.example.demo.dto.fastapi.FastApiRequest;
import com.example.demo.dto.fastapi.FastApiResponse;
import com.example.demo.entity.Game;
import com.example.demo.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    // 1. 새 게임 시작
    @PostMapping("/start")
    public ResponseEntity<Game> startGame(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(gameService.createNewGame(userId));
    }

    // 2. 턴 시작 (주가 변동 받아오기)
    @PostMapping("/{gameId}/turn/start")
    public ResponseEntity<FastApiResponse.TurnStart> startTurn(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.startTurn(gameId));
    }

    // 3. 턴 종료 (매수/매도 내역 보내고 정산받기)
    @PostMapping("/{gameId}/turn/end")
    public ResponseEntity<FastApiResponse.TurnEnd> endTurn(
            @PathVariable Long gameId,
            @RequestBody List<FastApiRequest.PlayerAction> actions) {
        return ResponseEntity.ok(gameService.endTurn(gameId, actions));
    }
}