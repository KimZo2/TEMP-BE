package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.auth.*;
import com.KimZo2.Back.dto.member.LoginResponseDTO;
import com.KimZo2.Back.entity.User;
import com.KimZo2.Back.exception.AdditionalSignupRequiredException;
import com.KimZo2.Back.repository.UserRepository;
import com.KimZo2.Back.util.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final KakaoUtil kakaoUtil;
    private final NaverUtil naverUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final GitHubUtil gitHubUtil;
    private final GoogleUtil googleUtil;

    public LoginResponseDTO oAuthLoginWithKakao(String accessCode, HttpServletResponse response) {
        log.info("AuthService - 카카오 인증 실행");

        // access_token 요청
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);

        // 사용자 정보 요청
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        // provider && providerId
        String provider = "kakao";
        String providerId = String.valueOf(kakaoProfile.getId());

        return handleSocialLogin(provider, providerId, response);
    }

    public LoginResponseDTO oAuthLoginWithNaver(String accessCode,String state, HttpServletResponse response) {
        log.info("AuthService - 네이버 인증 실행");

        // access_token 요청
        String accessToken = naverUtil.requestToken(accessCode, state);

        // 사용자 정보 요청
        NaverDTO.NaverUser naverUser = naverUtil.requestProfile(accessToken);

        // provider && providerId
        String provider = "naver";
        String providerId = String.valueOf(naverUser.getId());

        return handleSocialLogin(provider, providerId, response);
    }

    public LoginResponseDTO oAuthLoginWithGithub(String accessCode,String state, HttpServletResponse response) {
        log.info("AuthService - 깃허브 인증 실행");

        // access_token 요청
        String accessToken = gitHubUtil.requestToken(accessCode);

        // 사용자 정보 요청
        GitHubDTO.GithubUser githubUser = gitHubUtil.requestProfile(accessToken);

        // provider && providerId
        String provider = "github";
        String providerId = String.valueOf(githubUser.getId());

        return handleSocialLogin(provider, providerId, response);
    }

    public LoginResponseDTO oAuthLoginWithGoogle(String accessCode,String state, HttpServletResponse response) {
        log.info("AuthService - 구글 인증 실행");

        // access_token 요청
        String accessToken = googleUtil.requestToken(accessCode);

        // 사용자 정보 요청
        GoogleDTO.GoogleUser googleUser = googleUtil.requestProfile(accessToken);

        // provider && providerId
        String provider = "google";
        String providerId = String.valueOf(googleUser.getId());

        return handleSocialLogin(provider, providerId, response);
    }

    public LoginResponseDTO handleSocialLogin(String provider, String providerId, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByProviderAndProviderId(provider, providerId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = jwtUtil.generateToken(user.getNickname()); // 또는 user.getId()
            response.setHeader("Authorization", token);
            return new LoginResponseDTO(token, user.getNickname());
        } else {
            throw new AdditionalSignupRequiredException(provider, providerId);
        }
    }

    public void oAuthcreateNewUser(AdditionalSignupRequest dto, HttpServletResponse response) {
        log.info("AuthService - 회원가입 실행");

        String provider = dto.getProvider();
        String providerId = dto.getProviderId();
        String name = dto.getName();
        String nickName = dto.getNickname();
        String birthday = dto.getBirthday();


        // 회원가입 사용자 user로 build
        User newUser = User.builder()
                .provider(provider)
                .providerId(providerId)
                .name(name)
                .nickname(nickName)
                .birthday(birthday)
                .build();

        // 닉네임 중복 검사
        userService.validateDuplicateNickName(newUser);

        // user 저장
        userRepository.save(newUser);
    }
}
