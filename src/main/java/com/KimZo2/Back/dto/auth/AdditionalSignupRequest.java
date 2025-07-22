package com.KimZo2.Back.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdditionalSignupRequest {
    private String provider;
    private String providerId;
    private String name;
    private String nickname;
    private String birthday;
}
