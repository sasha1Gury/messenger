package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.ChatMessageDto;
import org.example.backend.model.ChatMessage;
import org.example.backend.model.MessageStatus;
import org.example.backend.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatId(chatMessageDto.getChatId());
        chatMessage.setSenderId(chatMessageDto.getSenderId());
        chatMessage.setRecipientId(chatMessageDto.getRecipientId());
        chatMessage.setSenderName(chatMessageDto.getSenderName());
        chatMessage.setRecipientName(chatMessageDto.getRecipientName());
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage.setTimestamp(new Date());
        chatMessage.setStatus(MessageStatus.DELIVERED);

        return chatMessageRepository.save(chatMessage);
    }
}
