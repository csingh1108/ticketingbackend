package com.graphql.practice.controller;

import com.graphql.practice.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/support/chat")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        System.out.println("Received message: " + message.getChatText());
        return message;
    }

}
