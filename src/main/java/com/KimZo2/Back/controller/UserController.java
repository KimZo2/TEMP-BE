package com.KimZo2.Back.controller;

import com.KimZo2.Back.dto.member.LoginResponseDTO;
import com.KimZo2.Back.dto.member.TokenResponse;
import com.KimZo2.Back.dto.member.userLoginDTO;
import com.KimZo2.Back.dto.member.userSignUpDTO;
import com.KimZo2.Back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody userSignUpDTO dto) {
        log.info("signUP 실행");
        userService.signUp(dto);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> logIn(@RequestBody userLoginDTO dto) {
        log.info("lgin 실행");
        LoginResponseDTO token = userService.logIn(dto);
        return ResponseEntity.ok(token);
    }
}