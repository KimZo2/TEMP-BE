package com.KimZo2.Back.controller;

import com.KimZo2.Back.dto.chat.ChatMessageDTO;
import com.KimZo2.Back.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 채팅 메시지 처리
     */
    @MessageMapping("/chat.send.{roomId}")
    public void sendMessage(@DestinationVariable String roomId,
                            @Payload ChatMessageDTO messageDto,
                            Principal principal) {

        messageDto.setRoomId(roomId);
        messageDto.setSender(principal.getName());

        // 저장
        ChatMessageDTO saved = chatService.saveMessage(messageDto);

        // broadcast
        messagingTemplate.convertAndSend(
                "/topic/chat/" + roomId,
                saved
        );
    }
}
