package com.example.demo.service;

import com.example.demo.dto.fastapi.FastApiRequest;
import com.example.demo.dto.fastapi.FastApiResponse;
import com.example.demo.entity.Game;
import com.example.demo.entity.Stock;
import com.example.demo.entity.User;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final FastApiClient fastApiClient;

    /**
     * 1. 게임 시작 (초기 자본금 500만원 세팅)
     */
    @Transactional
    public Game createNewGame(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Game newGame = Game.builder()
                .user(user)
                // @PrePersist 덕분에 currentTurn(1), currentMoney(5000000) 등이 자동 세팅됩니다.
                .build();

        return gameRepository.save(newGame);
    }

    /**
     * 2. 턴 시작 (주가 변동 계산 요청)
     */
    @Transactional(readOnly = true)
    public FastApiResponse.TurnStart startTurn(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("게임을 찾을 수 없습니다."));

        // DB에서 모든 상장 주식 정보를 꺼내옵니다.
        List<Stock> stocks = stockRepository.findAll();

        // FastAPI에 보낼 포장지(DTO) 규격에 맞게 변환합니다.
        List<FastApiRequest.StockInfo> stockInfos = stocks.stream()
                .map(s -> FastApiRequest.StockInfo.builder()
                        .stockId(s.getId())
                        .symbol(s.getSymbol())
                        .basePrice(s.getBasePrice())
                        .volatility(s.getVolatility())
                        .build())
                .collect(Collectors.toList());

        // FastAPI에 턴 시작 요청을 보냅니다! (뉴스나 이벤트는 일단 Null로 보냅니다)
        FastApiRequest.TurnStart requestDto = FastApiRequest.TurnStart.builder()
                .gameId(game.getId())
                .turnNum(game.getCurrentTurn())
                .stocks(stockInfos)
                .playerActions(new java.util.ArrayList<>()) // 빈 리스트 추가
                .marketSummary(new java.util.HashMap<>())   // 빈 딕셔너리 추가
                .build();

        // 우체부(FastApiClient)를 통해 파이썬 서버로 다녀옵니다.
        return fastApiClient.requestTurnStart(requestDto);
    }

    /**
     * 3. 턴 종료 (매수/매도 정산 및 DB 기록)
     */
    @Transactional
    public FastApiResponse.TurnEnd endTurn(Long gameId, List<FastApiRequest.PlayerAction> actions) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("게임을 찾을 수 없습니다."));

        // FastAPI에 정산 요청을 보냅니다!
        FastApiRequest.TurnEnd requestDto = FastApiRequest.TurnEnd.builder()
                .gameId(game.getId())
                .turnNum(game.getCurrentTurn())
                .playerActions(actions)
                .build();

        FastApiResponse.TurnEnd response = fastApiClient.requestTurnEnd(requestDto);

        // 정산되어 돌아온 내역을 바탕으로 유저의 실제 현금 잔고를 업데이트합니다.
        // (싱글 플레이어이므로 리스트의 첫 번째 데이터를 사용합니다)
        if (response.getPlayerMoney() != null && !response.getPlayerMoney().isEmpty()) {
            Long profitLoss = response.getPlayerMoney().get(0).getProfitLoss();
            game.setCurrentMoney(game.getCurrentMoney() + profitLoss);

            // 다음 턴으로 넘깁니다.
            game.setCurrentTurn(game.getCurrentTurn() + 1);
            gameRepository.save(game);
        }

        // (💡 참고: 여기에 TurnHistory 테이블에 JSON 데이터를 insert 하는 로직을 추가하면 완벽해집니다!)

        return response;
    }
}