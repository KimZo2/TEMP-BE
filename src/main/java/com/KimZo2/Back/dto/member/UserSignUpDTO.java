package com.KimZo2.Back.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignUpDTO {
    private String userId;
    private String userPw;
    private String name;
    private String nickname;
    private String birthday;
}
