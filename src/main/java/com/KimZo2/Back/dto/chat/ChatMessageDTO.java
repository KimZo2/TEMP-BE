package com.KimZo2.Back.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessageDTO {
    private String roomId;
    private String sender;
    private String content;

    private String createdAt;

}
