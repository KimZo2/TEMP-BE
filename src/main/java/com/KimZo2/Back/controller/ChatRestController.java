package com.KimZo2.Back.controller;

import com.KimZo2.Back.dto.chat.ChatMessageDTO;
import com.KimZo2.Back.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;


    // 나중에 이거 roomID 해쉬 되도록 변경 해야함
    @GetMapping("/{roomId}")
    public List<ChatMessageDTO> getChatHistory(@PathVariable String roomId) {
        return chatService.getChatHistory(roomId);
    }
}
