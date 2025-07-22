package com.KimZo2.Back.exception;

public class AdditionalSignupRequiredException extends RuntimeException {
    private final String provider;
    private final String providerId;
    private final String name;

    public AdditionalSignupRequiredException(String provider, String providerId, String name) {
        super("추가 회원가입이 필요한 사용자입니다.");
        this.provider = provider;
        this.providerId = providerId;
        this.name = name;
    }

    public String getProvider() { return provider; }
    public String getProviderId() { return providerId; }
    public String getName() { return name; }
}
