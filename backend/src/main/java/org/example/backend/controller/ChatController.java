package org.example.backend.controller;

import org.example.backend.dto.ChatMessageDto;
import org.example.backend.model.ChatMessage;
import org.example.backend.model.ChatNotification;
import org.example.backend.service.ChatMessageService;
import org.example.backend.service.ChatRoomService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private SimpMessagingTemplate messagingTemplate;
    private ChatMessageService chatMessageService;
    private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessage) {
        var chatId = chatRoomService
                .getChatId(String.valueOf(chatMessage.getSenderId()), String.valueOf(chatMessage.getRecipientId()), true);
        chatMessage.setChatId(chatId.orElseThrow(() -> new RuntimeException("Chat id not found")));

        ChatMessage saved = chatMessageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipientId()),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));
    }
}