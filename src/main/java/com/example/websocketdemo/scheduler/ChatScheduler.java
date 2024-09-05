package com.example.websocketdemo.scheduler;

import com.example.websocketdemo.handler.ChatWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ChatScheduler {
    private final ChatWebSocketHandler chatWebSocketHandler;

    public ChatScheduler(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

//    @Scheduled(fixedRate = 10000)
//    public void sendRegularMessage() {
//        chatWebSocketHandler.sendToAllUserMessage("Hello World " + System.currentTimeMillis());
//    }
}
