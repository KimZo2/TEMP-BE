package com.KimZo2.Back.util;

import com.KimZo2.Back.dto.member.KakaoDTO;
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

    public KakaoDTO requestToken(String accessCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl(tokenUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", client);
        body.add("redirect_uri", redirect);
        body.add("code", accessCode);

        return webClient.post()
                .uri("") // baseUrl이 이미 설정되어 있음
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(KakaoDTO.class)
                .block(); // 동기 처리
    }
}

