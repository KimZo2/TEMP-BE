package com.KimZo2.Back.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    @Id
    private Long id;

    private String provider; // kakao or naver or google or github

    private String providerId; // Oauth token

    private String name;

    @Indexed(unique = true)
    private String nickname; // 필수 항목 이걸로 사용자 구분

    private String birthday;
}
