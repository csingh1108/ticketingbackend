package com.graphql.practice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String chatText;
    private String senderName;
    private LocalDateTime timeSent;
    private MessageType type;
}
