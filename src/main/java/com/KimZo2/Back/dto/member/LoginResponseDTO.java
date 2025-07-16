package com.KimZo2.Back.dto.member;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
    private String token;
    private String nickname;

    public LoginResponseDTO(String token, String nickname) {
        this.token = token;
        this.nickname = nickname;
    }
}
