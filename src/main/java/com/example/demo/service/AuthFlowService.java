package com.example.demo.service;


import com.example.demo.dto.StandardFlowDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Keycloak 서버와 통신하여서 데이터를 수신합니다.
 *
 */
@FeignClient(
        name = "keycloak-auth-service",
        url = "<http://localhost:8080/realms/dev>"
)
@Service
public interface AuthFlowService {

    /**
     * Standard Flow : 로그인 페이지 출력 이후 로그인 성공 시 리다이렉트 경로로 데이터 리턴
     *
     * @param standardFlowDto 전송 객체 값 (form 데이터 형태로 전송)
     * @return 토큰 값 반환
     */
    @GetMapping(
            value = "/protocol/openid-connect/auth",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    String getStandardFlowLoginView(@ModelAttribute StandardFlowDto standardFlowDto);
}