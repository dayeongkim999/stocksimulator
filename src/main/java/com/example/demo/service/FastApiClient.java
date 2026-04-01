package com.example.demo.service;

import com.example.demo.dto.fastapi.FastApiRequest;
import com.example.demo.dto.fastapi.FastApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FastApiClient {

    private final RestTemplate restTemplate;

    //도커 내부 통신 방식
    private final String FASTAPI_BASE_URL = "http://fastapi_app:8000";
    /**
     * [턴 시작] 주가 변동 계산 요청
     */
    public FastApiResponse.TurnStart requestTurnStart(FastApiRequest.TurnStart requestDto) {
        String url = FASTAPI_BASE_URL + "/api/turn/start";

        // POST 요청을 보내고, 결과를 FastApiResponse.TurnStart 객체로 받아옵니다!
        ResponseEntity<FastApiResponse.TurnStart> response = restTemplate.postForEntity(
                url,
                requestDto,
                FastApiResponse.TurnStart.class
        );

        return response.getBody();
    }

    /**
     * [턴 종료] 매수/매도 내역을 통한 정산 요청
     */
    public FastApiResponse.TurnEnd requestTurnEnd(FastApiRequest.TurnEnd requestDto) {
        String url = FASTAPI_BASE_URL + "/api/turn/end";

        // POST 요청을 보내고, 결과를 FastApiResponse.TurnEnd 객체로 받아옵니다!
        ResponseEntity<FastApiResponse.TurnEnd> response = restTemplate.postForEntity(
                url,
                requestDto,
                FastApiResponse.TurnEnd.class
        );

        return response.getBody();
    }
}