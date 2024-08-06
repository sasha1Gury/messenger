package org.example.backend.service;

import org.example.backend.model.ChatRoom;
import org.example.backend.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId);

        if (chatRoomOpt.isPresent()) {
            return Optional.of(chatRoomOpt.get().getChatId());
        } else if (createIfNotExist) {
            String chatId = String.format("%s_%s", senderId, recipientId);
            ChatRoom senderRecipient = new ChatRoom(chatId, senderId, recipientId);
            ChatRoom recipientSender = new ChatRoom(chatId, recipientId, senderId);
            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);
            return Optional.of(chatId);
        } else {
            return Optional.empty();
        }
    }
}
