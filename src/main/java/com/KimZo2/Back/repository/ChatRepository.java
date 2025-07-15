package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository {
    ChatMessage save(ChatMessage entity);

    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(String roomId);
}

