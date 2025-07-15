package com.KimZo2.Back.controller;

import com.KimZo2.Back.dto.member.TokenResponse;
import com.KimZo2.Back.dto.member.userLoginDTO;
import com.KimZo2.Back.dto.member.userSignUpDTO;
import com.KimZo2.Back.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody userSignUpDTO dto) {
        userService.signUp(dto);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody userLoginDTO dto) {
        String token = userService.logIn(dto);
        return ResponseEntity.ok(token);
    }
}
