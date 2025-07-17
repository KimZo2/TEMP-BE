package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.member.KakaoDTO;
import com.KimZo2.Back.entity.User;
import com.KimZo2.Back.repository.UserRepository;
import com.KimZo2.Back.util.JwtUtil;
import com.KimZo2.Back.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoDTO oAuthToken = kakaoUtil.requestToken(accessCode);

    }
}
