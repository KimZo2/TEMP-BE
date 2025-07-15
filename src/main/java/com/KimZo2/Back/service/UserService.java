package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.member.userLoginDTO;
import com.KimZo2.Back.dto.member.userSignUpDTO;
import com.KimZo2.Back.exception.DuplicateUserIdException;
import com.KimZo2.Back.entity.User;
import com.KimZo2.Back.repository.UserRepository;
import com.KimZo2.Back.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 유저 회원가입
    @Transactional
    public void signUp(userSignUpDTO dto) {
        String userId = dto.getUserId();
        String userPw = passwordEncoder.encode(dto.getUserPw());
        String nickName = dto.getNickname();

        // 중복 검사
        validateDuplicateUser(userId);

        // User 엔티티 설정
        User user = new User();
        user.setUserId(userId);
        user.setUserPw(userPw);
        user.setNickName(nickName);

        userRepository.save(user);
    }

    // 유저 아이디 중복 검사
    public void validateDuplicateUser(String userId) {
        boolean exists = userRepository.existByUserId(userId);
        if (exists) {
            throw new DuplicateUserIdException("이미 존재하는 아이디입니다: " + userId);
        }
    }

    // 유저 로그인
    public String logIn(userLoginDTO dto) {
        String userId = dto.getUserId();
        String rawPw = dto.getUserPw();

        // 유저 존재 여부 체크
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(rawPw, user.getUserPw())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(userId);

        return token;
    }
}
