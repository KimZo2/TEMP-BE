package com.KimZo2.Back.dto.auth;


import lombok.Getter;


public class KakaoDTO {

    @Getter
    public static class OAuthToken {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private Long expires_in;
        private String scope;
        private Long refresh_token_expires_in;
    }

    @Getter
    public static class KakaoProfile {
        private Long id; // ✅ providerId로 사용할 고유 식별자
        private KakaoAccount kakao_account;

        @Getter
        public static class KakaoAccount {
            private Profile profile;

            @Getter
            public static class Profile {
                private String nickname; // ✅ 이것만 사용할 예정 (실제 이름으로)
            }
        }
    }

}
