package com.aanand.demo;

import com.aanand.demo.services.ChatMessageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ChatMessageScheduler {
    private ChatMessageService chatMessageService;
    // Runs every 5 seconds
    @Scheduled(fixedRate = 5000)
    void runEveryFiveSeconds() {
        System.out.println("Running: " + LocalDateTime.now());
        //hard code this value
        chatMessageService.processUserMessage(UUID.randomUUID());
    }
}
