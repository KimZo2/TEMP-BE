package com.KimZo2.Back.util;

import com.KimZo2.Back.dto.auth.KakaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class KakaoUtil {

    @Value("${kakao.auth.client}")
    private String client;

    @Value("${kakao.auth.redirect}")
    private String redirect;

    private final String kakao_url = "https://kauth.kakao.com/oauth/token";

    public KakaoDTO.OAuthToken requestToken(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", client);
        formData.add("redirect_uri", redirect);
        formData.add("code", code);

        return webClient.post()
                .uri("/oauth/token")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(HttpStatusCode::isError, res ->
                        res.bodyToMono(String.class).doOnNext(errorBody ->
                                log.error("카카오 토큰 요청 실패: {}", errorBody)
                        ).then(Mono.error(new RuntimeException("토큰 요청 실패")))
                )
                .bodyToMono(KakaoDTO.OAuthToken.class)
                .block();
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

        log.info("kakaoNickname: {}", kakaoProfile.getKakao_account().getProfile().getNickname());

        return kakaoProfile;
    }

}

