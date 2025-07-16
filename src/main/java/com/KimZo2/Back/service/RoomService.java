package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.room.EnterRoomMessage;
import com.KimZo2.Back.dto.room.RoomInfoDto;
import com.KimZo2.Back.entity.Room;
import com.KimZo2.Back.entity.Character;
import com.KimZo2.Back.repository.RoomRepository;
import com.KimZo2.Back.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomRepository roomRepository;
    private final JwtUtil jwtUtil;

    // RoomService.java
    private final Map<String, List<Character>> roomCharacterMap = new HashMap<>();

    public Room createRoom(String id, String name, String creator) {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.setCreator(creator);
        room.setCreateAt(java.time.LocalDateTime.now());

        return roomRepository.save(room);
    }

    /**
     * 방 입장 처리
     */
    public RoomInfoDto enterRoom(String token, String roomId, String nickname) {
        String userId = jwtUtil.getUserId(token);

        List<Character> characters = roomCharacterMap
                .computeIfAbsent(roomId, k -> new ArrayList<>());

        boolean alreadyExists = characters.stream()
                .anyMatch(c -> c.getUserId().equals(userId));

        if (!alreadyExists) {
            characters.add(new Character(userId, nickname));
        }

        return new RoomInfoDto(roomId, characters);
    }

}
