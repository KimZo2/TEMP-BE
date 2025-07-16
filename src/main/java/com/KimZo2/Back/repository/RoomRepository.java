package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findById(String id);
    List<Room> findAll();
}
