package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.member.LoginResponseDTO;
import com.KimZo2.Back.dto.member.UserLoginDTO;
import com.KimZo2.Back.dto.member.UserSignUpDTO;
import com.KimZo2.Back.exception.DuplicateUserIdException;
import com.KimZo2.Back.entity.User;
import com.KimZo2.Back.exception.DuplicateUserNickNameException;
import com.KimZo2.Back.repository.UserRepository;
import com.KimZo2.Back.util.JwtUtil;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public void validateDuplicateNickName(User user) {
        log.info("UserService - 닉네임 중복 검사 실행");

        if (userRepository.existsByNickname(user.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // handleDuplicateKeyException 처리 해놓음
        userRepository.save(user);
    }
}
