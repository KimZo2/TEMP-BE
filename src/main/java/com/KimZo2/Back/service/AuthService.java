package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.auth.AdditionalSignupRequest;
import com.KimZo2.Back.dto.auth.KakaoDTO;
import com.KimZo2.Back.dto.auth.NaverDTO;
import com.KimZo2.Back.entity.User;
import com.KimZo2.Back.exception.AdditionalSignupRequiredException;
import com.KimZo2.Back.repository.UserRepository;
import com.KimZo2.Back.util.JwtUtil;
import com.KimZo2.Back.util.KakaoUtil;
import com.KimZo2.Back.util.NaverUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoUtil kakaoUtil;
    private final NaverUtil naverUtil;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public User oAuthLoginWithKakao(String accessCode, HttpServletResponse response) {

        // access_token 요청
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);

        // 사용자 정보 요청
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        String provider = "kakao";
        String providerId = String.valueOf(kakaoProfile.getId());
        String name = kakaoProfile.getKakao_account().getProfile().getNickname();

        // 이미 가입된 유저인지 확인
        Optional<User> optionalUser = userRepository.findByProviderAndProviderId(provider, providerId);

        if (optionalUser.isPresent()) {
            // 이미 가입된 사용자면 JWT 발급
            User user = optionalUser.get();
            String token = jwtUtil.generateToken(user.getNickname());
            response.setHeader("Authorization", token);
            return user;
        } else {
            // 회원가입이 아직 안 된 사용자
            throw new AdditionalSignupRequiredException(provider, providerId, name);
        }
    }

    public User oAuthLoginWithNaver(String code, String state, HttpServletResponse response) {

        // access_token 요청
        String accessToken = naverUtil.requestToken(code, state);

        // 사용자 정보 요청
        NaverDTO.NaverUser naverUser = naverUtil.requestProfile(accessToken);

        String provider = "naver";
        String providerId = String.valueOf(naverUser.getId());
        String name = naverUser.getName();

        // 사용자 찾기 or 생성
        Optional<User> optionalUser = userRepository.findByProviderAndProviderId(provider, providerId);

        if (optionalUser.isPresent()) {
            // 이미 가입된 사용자면 JWT 발급
            User user = optionalUser.get();
            String token = jwtUtil.generateToken(user.getNickname());
            response.setHeader("Authorization", token);
            return user;
        } else {
            // 회원가입이 아직 안 된 사용자
            throw new AdditionalSignupRequiredException(provider, providerId, name);
        }
    }

    public User oAuthcreateNewUser(AdditionalSignupRequest dto, HttpServletResponse response) {
        // 회원가입 사용자 user로 build
        User newUser = User.builder()
                .provider(dto.getProvider())
                .providerId(dto.getProviderId())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .build();

        // user 저장
        User user = userRepository.save(newUser);
        String token = jwtUtil.generateToken(user.getNickname());
        response.setHeader("Authorization", token);
        return user;
    }
}
