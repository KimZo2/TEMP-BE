package com.KimZo2.Back.dto.member;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoDTO {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
    private String scope;
    private Long refresh_token_expires_in;
}
