package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.room.RoomCreateDTO;
import com.KimZo2.Back.entity.Room;
import com.KimZo2.Back.entity.User;
import com.KimZo2.Back.repository.DBRepository;
import com.KimZo2.Back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DBRepository dbRepository;

    // 방 생성 -> PostGre랑 Redis에 모두 저장 필요
    public UUID createRoom(RoomCreateDTO dto) {
        String creatorNickname = dto.getCreatorNickname();

        // user 정보 찾기
        User creator = userRepository.findByNickname(creatorNickname);
        if(creator == null) throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");

        // 활성화 되어 있는 방 중 중복 이름 판단 - Redis
        if(validateDuplicatedRoomName(dto.getName())) {
            throw new IllegalArgumentException("이름이 동일한 방이 존재합니다.");
        }

        // PostGre 레코드 생성
        Room newRoom = Room.builder()
                .name(dto.getName())
                .maxParticipants(dto.getMaxParticipants())
                .isPrivate(false)
                .roomTime(dto.getRoomTime())
                .currentParticipants(0)
                .creator(creator)
                .build();

        // room private, 비밀번호 Encode 설정
        if(dto.isPrivate()) {
            String pwdRaw = dto.getPassword();
            if(pwdRaw == null || pwdRaw.isBlank()) throw new IllegalArgumentException("비밀방 비밀번호 필요");
            newRoom.makePrivate(passwordEncoder.encode(pwdRaw));
        }

        // PostGre 커밋
        dbRepository.save(newRoom);



        // Reids 로직

        // Redis에는 비밀번호 저장해두지 않기

        return null;
    }

    // 방 이름 중복 조회 -> 현재 방의 상태가 active인 방 중 중복 이름 존재하는지 확인하는 로직
    public boolean validateDuplicatedRoomName(String roomName) {

        return true;
    }

    // 방 조회 -> 현재 방의 상태가 active한 방이 있는지 확인


    // 방 입장
    public void enterRoom() {
        // 방 ID를 기준으로 방 찾기
            // 만약 방이 private이라면 비밀번호 요구  -> PostGre에서 검증하기

        // 현재 방의 인원이 가득 찼는지 확인

        // 방 입장 완료 응답
    }


    // 방 비밀번호 조회
        // 방 ID를 기준으로 방 찾기

        // 비밀번호 확인
            // 만약 비밀번호가 틀리다면 비밀번호 재요청 응답

        // 현재 방의 인원이 가득 찼는지 확인

        // 방 입장 완료 응답


    // 현재 방의 인원이 가득 찼는지 확인하는 로직
        // 현재 방의 인원이 가득 찼다면 입장 불가 응답


}
