package com.KimZo2.Back.util;

import com.KimZo2.Back.dto.member.KakaoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@Slf4j
public class KakaoUtil {

    @Value("${kakao.auth.client}")
    private String client;

    @Value("${kakao.auth.redirect}")
    private String redirect;

    private final String tokenUri = "https://kauth.kakao.com/oauth/token";

    public KakaoDTO.OAuthToken requestToken(String accessCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl(tokenUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", client);
        body.add("redirect_uri", redirect);
        body.add("code", accessCode);

        KakaoDTO.OAuthToken oAuthToken = webClient.post()
                .uri("/oauth/token")
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(KakaoDTO.OAuthToken.class)
                .block(); // 동기 처리

        log.info("oAuthToken : {}", oAuthToken.getAccess_token());

        return oAuthToken;
    }

    public KakaoDTO.KakaoProfile requestProfile(KakaoDTO.OAuthToken oAuthToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oAuthToken.getAccess_token())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        KakaoDTO.KakaoProfile kakaoProfile = webClient.get()
                .uri("/v2/user/me")
                .retrieve()
                .bodyToMono(KakaoDTO.KakaoProfile.class)
                .block(); // 동기 방식으로 바로 받음

        log.info("kakaoProfile: {}", kakaoProfile.getKakao_account().getEmail());

        return kakaoProfile;
    }

}

