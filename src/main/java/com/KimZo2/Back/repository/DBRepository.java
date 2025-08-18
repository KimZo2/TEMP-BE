package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DBRepository extends JpaRepository<Room, UUID> {
}
