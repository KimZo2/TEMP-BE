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

    public KakaoDTO.KakaoProfile requestProfile(String oAuthToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oAuthToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        KakaoDTO.KakaoProfile kakaoProfile = webClient.get()
                .uri("/v2/user/me")
                .retrieve()
                .bodyToMono(KakaoDTO.KakaoProfile.class)
                .block(); // 동기 방식으로 바로 받음

        return kakaoProfile;
    }

}

