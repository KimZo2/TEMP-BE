package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.Room;

public interface RoomRepository {

    // 방 생성 ( 저장 )
    void save(Room room);

    // 방 조회
    Room findByIsPrivate();

    // 방 제거

}
