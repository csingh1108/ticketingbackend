package com.graphql.practice.listener;

import com.graphql.practice.domain.ChatMessage;
import com.graphql.practice.domain.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    public void handleWebSocketConnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        if(username != null){
            log.info("User Disconnected: {}" + username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.SYSTEM)
                    .senderName(username)
                    .build();

            messagingTemplate.convertAndSend("/user/support" , chatMessage);
        }
    }
}
