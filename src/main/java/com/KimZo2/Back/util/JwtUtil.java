package com.KimZo2.Back.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 암호화 키
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 1시간 유효
    private final long expirationMs = 60 * 60 * 1000;

    // 토근 생성
    public String generateToken(String userNickName) {
        return Jwts.builder()
                .setSubject(userNickName) // 토큰 주제(유저명)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)  // 서명 (변조 방지)
                .compact();
    }

    // JWT 토큰 안에서 사용자 식별을 위한
    public String getUserId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            // 유효하지 않은 토큰
            return null;
        }
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
