package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/keycloak")
public class KeycloakController {
    /*
    * keycloak 로그인 성공 이후 처리
    *
    * @param code
    * @param state
    * @param session_state
    * @param error
    * @param error_description
    * @return
    * */
    @GetMapping("/callback")
    public ResponseEntity<String> loginCallback(
            @RequestParam String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String session_state,
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String error_description
    ){
        System.out.println("loginCallback");
        System.out.println("code:"+code);
        System.out.println("state:"+state);
        System.out.println("session_state:"+session_state);
        System.out.println("error:"+error);
        System.out.println("error_description:"+error_description);

        return new ResponseEntity<>(code, HttpStatus.OK);
    }
}
