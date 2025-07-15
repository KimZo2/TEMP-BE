package com.KimZo2.Back.dto.member;

import lombok.Getter;

@Getter
public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
