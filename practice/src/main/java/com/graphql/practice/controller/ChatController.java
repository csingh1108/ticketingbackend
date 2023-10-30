package com.graphql.practice.controller;

import com.graphql.practice.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate){
    }

    @MessageMapping("/support/{userId}")
    @SendTo("/user/{userId}/support")
    public ChatMessage supportMessage(@DestinationVariable String userId, @Payload ChatMessage message) {

        return message;
    }


}
