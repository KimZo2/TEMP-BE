package com.KimZo2.Back.config;

import com.KimZo2.Back.entity.Room;
import com.KimZo2.Back.service.RoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitDataRunner implements CommandLineRunner {

    private final RoomService roomService;

    public InitDataRunner(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * 방 데이터 초기화
     */
    @Override
    public void run(String... args) throws Exception {
        roomService.createRoom(
                "room1",
                "first",
                "admin"
        );

        System.out.println("서버 시작 시 room1 생성 완료!");
    }
}
