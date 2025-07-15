package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.ChatMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("local")
public class MemoryChatRepository implements ChatRepository {

    private final List<ChatMessage> store = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(1L);

    @Override
    public ChatMessage save(ChatMessage entity) {
        entity.setId(idGen.getAndIncrement());
        store.add(entity);
        return entity;
    }

    @Override
    public List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(String roomId) {
        return store.stream()
                .filter(e -> e.getRoomId().equals(roomId))
                .sorted(Comparator.comparing(ChatMessage::getCreatedAt))
                .toList();
    }
}
