package com.KimZo2.Back.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OAuthLoginRequest {
    private String provider;
    private String code;
}
