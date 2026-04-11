package com.aanand.demo.repositories;

import com.aanand.demo.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID>  {
    // Using method naming convention
    ChatMessage findFirstByChatIdOrderByCreatedAtDesc(UUID chatId);
}
