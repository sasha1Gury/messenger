package org.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;

    public ChatRoom() {
    }

    public ChatRoom(String chatId, String senderId, String recipientId) {
        this.id = senderId + "_" + recipientId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }
}