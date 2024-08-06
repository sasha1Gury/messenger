package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.example.backend.model.ChatMessage;
import org.example.backend.model.MessageStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link ChatMessage}
 */
@Data
public class ChatMessageDto implements Serializable {
    Long id;
    String chatId;
    Long senderId;
    Long recipientId;
    String senderName;
    String recipientName;
    String content;
    Date timestamp;
    MessageStatus status;
}