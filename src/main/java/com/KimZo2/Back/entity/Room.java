package com.KimZo2.Back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Room {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String creator;

    private LocalDateTime createAt;

}
