package com.KimZo2.Back.util;

import com.KimZo2.Back.dto.auth.GitHubDTO;
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
public class GitHubUtil {

    @Value("${github.auth.client-id}")
    private String clientId;

    @Value("${github.auth.client-secret}")
    private String clientSecret;

    @Value("${github.auth.redirect-uri}")
    private String redirectUri;

    @Value("${github.auth.token-uri}")
    private String tokenUri;

    @Value("${github.auth.profile-uri}")
    private String profileUri;

    private final WebClient.Builder webClientBuilder;

    // 액세스 토큰 요청
    public String requestToken(String code) {
        WebClient webClient = webClientBuilder.build();

        return webClient.post()
                .uri(tokenUri)
                .header(HttpHeaders.ACCEPT, "application/json")
                .bodyValue(
                        new GitHubDTO.GithubTokenRequest(
                                clientId,
                                clientSecret,
                                code,
                                redirectUri
                        )
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(tokenResponse -> tokenResponse.get("access_token").asText())
                .block();
    }

    // 사용자 정보 요청
    public GitHubDTO.GithubUser requestProfile(String accessToken) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri(profileUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(GitHubDTO.GithubUser.class)
                .block();
    }
}
