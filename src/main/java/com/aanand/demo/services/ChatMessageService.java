package com.aanand.demo.services;

import com.aanand.demo.ChatMessage;
import com.aanand.demo.services.LinqMessageResponse;
import com.aanand.demo.services.MessagePart;
import com.aanand.demo.repositories.ChatMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final WebClient linqWebClient;
    private final MovieService movieService;

    public ChatMessageService(ChatMessageRepository chatMessageRepository,
                              WebClient linqWebClient,
                              MovieService movieService) {
        this.chatMessageRepository = chatMessageRepository;
        this.linqWebClient = linqWebClient;
        this.movieService = movieService;
    }

    public void processUserMessage(UUID chatId){
        ChatMessage chatMsg = chatMessageRepository.findFirstByChatIdOrderByCreatedAtDesc(chatId);
        if(chatMsg.isProcessed()){
            return;
        }

        //fetch msg using linq API
        LinqMessageResponse linqChatMsg = fetchLinqMsg(chatMsg.getMsgId());

        //split the msg based on space
        for (MessagePart part : linqChatMsg.getParts()) {
            if (part.getType().equals("text")) {
                String text = part.getValue();
                String[] words = text.split(" ");
                String command = words[0].toLowerCase();

                switch (command) {
                    case "book":
                        // display all movies
                        sendAllMovies(chatId);
                        break;
                    case "movie":
                        // show seats for movie number words[1]
//                        displayAllMovieSeats();
                        break;
                    case "seat":
                        // update seat entry and send success
//                        updateSeat();
                        break;
                    default:
                        // unknown command
                        break;
                }
            }
        }

        //based on first string, if its book display all movies (linq api)
        //if its movie, show seats for the specific movie number coming as second value (linq api)
        //if its seat, update seat entry for is_booked and send success msg

        chatMsg.setProcessed(true);
        chatMessageRepository.save(chatMsg);
    }

    private void sendAllMovies(UUID chatId){
        String reqdTxt = movieService.displayAllMovies();
        sendLinqMsg(chatId, reqdTxt);
    }

    private void sendLinqMsg(UUID chatId, String reqdTxt){
        linqWebClient.post()
                .uri("chats/{chatId}/messages", chatId)
                .bodyValue(Map.of(
                        "message", Map.of(
                                "parts", List.of(
                                        Map.of(
                                                "type", "text",
                                                "value", reqdTxt
                                        )
                                )
                        )
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // makes it synchronous
    }


    private LinqMessageResponse fetchLinqMsg(UUID id){
        return linqWebClient.get()
                .uri("messages/{messageId}", id)
                .retrieve()
                .bodyToMono(LinqMessageResponse.class)
                .block(); // makes it synchronous
    }
}
