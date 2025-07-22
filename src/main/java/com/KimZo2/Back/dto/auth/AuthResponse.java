package com.KimZo2.Back.dto.auth;

import com.KimZo2.Back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String name;
    private String nickname;
    private boolean needNickname; // nickname 없으면 true

    public AuthResponse(User user) {
        this.userId = user.getId();
        this.name = user.getName(); // 카카오 닉네임
        this.nickname = user.getNickname();
        this.needNickname = (user.getNickname() == null);
    }
}
