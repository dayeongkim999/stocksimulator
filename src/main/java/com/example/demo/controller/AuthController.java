//package com.example.demo.controller;
//
//import com.example.demo.dto.StandardFlowDto;
//import com.example.demo.service.AuthFlowService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * OIDC 인증 플로우 구성
// */
//@Slf4j
//@RestController
//@RequestMapping("/api/v1/keycloak/authFlow")
//public class AuthFlowController {
//
//    private final AuthFlowService authFlowService;
//
//    public AuthFlowController(AuthFlowService authFlowService) {
//        this.authFlowService = authFlowService;
//    }
//
//    /**
//     * Standard Flow : 로그인 페이지 출력 및 로그인
//     *
//     * @param response_type
//     * @param client_id
//     * @param redirect_uri
//     * @param scope
//     * @param state
//     * @return
//     */
//    @GetMapping("/standardFlow")
//    public ResponseEntity<Object> getStandardFlow(
//            @RequestParam String response_type,
//            @RequestParam String client_id,
//            @RequestParam String redirect_uri,
//            @RequestParam(required = false) String scope,
//            @RequestParam(required = false) String state
//    ) {
//        StandardFlowDto standardFlowDto = StandardFlowDto.builder()
//                .response_type(response_type)
//                .client_id(client_id)
//                .redirect_uri(redirect_uri)
//                .scope(scope)
//                .state(state)
//                .build();
//        try {
//            String authorizationUrl = authFlowService.getStandardFlowLoginView(standardFlowDto);
//            return new ResponseEntity<>(authorizationUrl, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Standard flow failed", e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//}

package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(userService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}