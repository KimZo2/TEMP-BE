package com.KimZo2.Back.controller;

import com.KimZo2.Back.dto.room.EnterRoomMessage;
import com.KimZo2.Back.dto.room.RoomInfoDto;
import com.KimZo2.Back.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RoomSocketController {
    private final RoomService roomService;

    @MessageMapping("/room.enter")
    @SendTo("/topic/room")
    public RoomInfoDto enterRoom(EnterRoomMessage message, SimpMessageHeaderAccessor headerAccessor) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");

        RoomInfoDto roomInfoDto = roomService.enterRoom(
                token,
                message.getRoomId(),
                message.getNickname()
        );

        return roomInfoDto;
    }
}
