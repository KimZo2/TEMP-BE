package com.KimZo2.Back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userId;

    private String userPw;

    private String provider; // kakao or naver or null

    private String providerId; // Oauth toekn

    private String name;

    private String nickname; // 필수 항목 이걸로 사용자 구분

    private String birthday;
}
