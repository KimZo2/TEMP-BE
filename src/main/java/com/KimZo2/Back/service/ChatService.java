package com.KimZo2.Back.service;

import com.KimZo2.Back.dto.chat.ChatMessageDTO;
import com.KimZo2.Back.entity.ChatMessage;
import com.KimZo2.Back.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    /**
     * 과거 채팅 내역 조회
     */
    public List<ChatMessageDTO> getChatHistory(String roomId) {
        return chatRepository.findByRoomIdOrderByCreatedAtAsc(roomId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 채팅 저장
     */
    public ChatMessageDTO saveMessage(ChatMessageDTO dto) {
        ChatMessage entity = new ChatMessage();
        entity.setRoomId(dto.getRoomId());
        entity.setSender(dto.getSender());
        entity.setContent(dto.getContent());
        entity.setCreatedAt(LocalDateTime.now());

        ChatMessage saved = chatRepository.save(entity);
        return convertToDto(saved);
    }

    /**
     * DTO 변환
     */
    private ChatMessageDTO convertToDto(ChatMessage entity) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setRoomId(entity.getRoomId());
        dto.setSender(entity.getSender());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt().toString());
        return dto;
    }

}
