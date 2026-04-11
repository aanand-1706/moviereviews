package com.aanand.demo;

import com.aanand.demo.services.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ChatMessageScheduler {
    private final ChatMessageService chatMessageService;

    public ChatMessageScheduler(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // Runs every 5 seconds
    @Scheduled(fixedRate = 5000)
    void runEveryFiveSeconds() {
        System.out.println("Running: " + LocalDateTime.now());
        //hard code this value
        chatMessageService.processUserMessage(UUID.fromString("d420ebad-6e00-4edf-bc69-6075b330ad78"));
    }
}
