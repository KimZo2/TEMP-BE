package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.Room;

import java.util.HashMap;

public class RoomMemoryRepository implements RoomRepository {
    HashMap<Long,Room> map = new HashMap<>();

    @Override
    public void save(Room room){
    }

    @Override
    public Room findByIsPrivate() {
        return null;
    }

    @Override
    public Room findByName() {
        return null;
    }

}

