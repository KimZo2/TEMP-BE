package com.KimZo2.Back.controller;

import com.KimZo2.Back.dto.auth.AdditionalSignupRequest;
import com.KimZo2.Back.dto.auth.AuthResponse;
import com.KimZo2.Back.entity.User;
import com.KimZo2.Back.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode,
                                        HttpServletResponse response)
    {
        log.info("/login/kakao  -  실행");

        User user = authService.oAuthLoginWithKakao(accessCode, response);

        return ResponseEntity.ok(new AuthResponse(user));
    }

    @GetMapping("/login/naver")
    public ResponseEntity<?> NaverLogin(
            @RequestParam String code,
            @RequestParam String state,
            HttpServletResponse response)
    {
        log.info("/login/naver  -  실행");

        User user = authService.oAuthLoginWithNaver(code, state, response);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> OAuthSignup(@RequestBody AdditionalSignupRequest dto,
                                                      HttpServletResponse response) {
        // User 생성
        User user = authService.oAuthcreateNewUser(dto, response);

        return ResponseEntity.ok(user);
    }
}
