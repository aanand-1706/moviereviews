package com.aanand.demo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"chat_id", "msg_id"}
        )
)
public class ChatMessage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //to specify primary key
    private UUID id;

    @Column(name = "chat_id")
    private UUID chatId;

    @Column(name = "msg_id")
    private UUID msgId;

    public ChatMessage(UUID msgId, UUID chatId, boolean processed) {
        this.msgId = msgId;
        this.chatId = chatId;
        this.processed = processed;
    }

    @Column(name = "processed")
    private boolean processed;

    public ChatMessage() {

    }

    public UUID getMsgId() {
        return msgId;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public void setMsgId(UUID msgId) {
        this.msgId = msgId;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public UUID getId() {
        return id;
    }

    public UUID getChatId() {
        return chatId;
    }
}
