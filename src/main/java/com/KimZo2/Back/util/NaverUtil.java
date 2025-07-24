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

    @Value("${naver.auth.profile-uri}")
    private String profileUri;

    private final WebClient.Builder webClientBuilder;

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
