package com.example.websocketdemo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOG.info("Got connection established {}", session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOG.info("Got connection closed {}", session.getId());
        sessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        LOG.info("Get input message: {}, session id: {}", payload, session.getId());
        session.sendMessage(new TextMessage("Session id: " + session.getId() + ", content: " + payload));
    }

    public void sendToAllUserMessage(String message) {
        for (WebSocketSession session : sessions) {
            if(session != null && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    LOG.warn("Failed to send message", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
