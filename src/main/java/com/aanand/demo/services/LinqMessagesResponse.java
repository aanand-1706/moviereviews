package com.aanand.demo.services;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class LinqMessagesResponse {
    private List<LinqMessageResponse> messages;
    @JsonProperty("next_cursor")
    private String nextCursor;

    public List<LinqMessageResponse> getMessages() {
        return messages;
    }

    public String getNextCursor() {
        return nextCursor;
    }
}
