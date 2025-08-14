package com.KimZo2.Back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Room {
    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String creatorNickname;

    private long maxParticipants;

    private boolean isPrivate;

    private String password;

    private String roomTime;

    private long currentParticipants;

    private long roomType;
}
