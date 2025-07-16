package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.Room;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryRoomRepository implements RoomRepository {

    private final Map<String, Room> rooms = new HashMap<>();

    public MemoryRoomRepository() {
        Room room = new Room();
        room.setId("room1");
        room.setName("first");
        room.setCreator("admin");
        room.setCreateAt(LocalDateTime.now());

        rooms.put("room1", room);
    }


    @Override
    public Room save(Room room) {
        return null;
    }

    @Override
    public Optional<Room> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        return List.of();
    }
}
