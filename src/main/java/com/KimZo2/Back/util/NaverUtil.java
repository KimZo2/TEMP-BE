package com.KimZo2.Back.util;

import com.KimZo2.Back.dto.auth.NaverDTO;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverUtil {

    @Value("${naver.auth.client-id}")
    private String clientId;

    @Value("${naver.auth.client-secret}")
    private String clientSecret;

    @Value("${naver.auth.redirect-uri}")
    private String redirectUri;

    @Value("${naver.auth.token-uri}")
    private String tokenUri;

    @Value("${naver.auth.profile-uri}")
    private String profileUri;

    private final WebClient.Builder webClientBuilder;

    public String requestToken(String code, String state) {
        WebClient webClient = webClientBuilder.build();

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("nid.naver.com")
                        .path("/oauth2.0/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .queryParam("state", state)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(tokenResponse -> tokenResponse.get("access_token").asText())
                .block();
    }

    public NaverDTO.NaverUser requestProfile(String accessToken) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri(profileUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(NaverDTO.class)
                .map(NaverDTO::getResponse)
                .block();
    }
}
