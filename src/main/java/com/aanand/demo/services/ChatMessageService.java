package com.aanand.demo.services;

import com.aanand.demo.ChatMessage;
import com.aanand.demo.Movie;
import com.aanand.demo.Seat;
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
    private final SeatService seatService;

    public ChatMessageService(ChatMessageRepository chatMessageRepository,
                              WebClient linqWebClient,
                              MovieService movieService, SeatService seatService) {
        this.chatMessageRepository = chatMessageRepository;
        this.linqWebClient = linqWebClient;
        this.movieService = movieService;
        this.seatService = seatService;
    }

    public void processUserMessage(UUID chatId){
        List<LinqMessageResponse> allChatMsgs = fetchLinqMsgFromChat(chatId, 1);
        LinqMessageResponse latestChatMsg = allChatMsgs.get(0);

        ChatMessage chatMsg = chatMessageRepository.findChatMessageByChatIdAndMsgId(chatId, latestChatMsg.getId());
        if (chatMsg == null){
            if(latestChatMsg.isFromMe()) {
                System.out.println("Last msg not from user");
                return;
            }
            System.out.println("Processing new msg");
            chatMsg = new ChatMessage(latestChatMsg.getId(), chatId, false);
        } else if(chatMsg.isProcessed()){
            System.out.println("Already processed msgId: " + latestChatMsg.getId());
            return;
        }

        //split the msg based on space
        for (MessagePart part : latestChatMsg.getParts()) {
            if (part.getType().equals("text")) {
                String text = part.getValue();
                String[] words = text.split(" ");
                String command = words[0].toLowerCase();

                switch (command) {
                    case "book":
                        // display all movies
                        displayAllMovies(chatId);
                        break;
                    case "movie":
                        // show seats for movie number words[1]
                        displayAvailableSeats(chatId, Integer.parseInt(words[1]));
                        break;
                    case "seat":
                        String[] values = words[1].split("#");
                        Integer movieId = Integer.parseInt(values[0]), seatNo=Integer.parseInt(values[1]);
                        allotSeatToUser(chatId, movieId, seatNo);
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

    private void allotSeatToUser(UUID chatId, Integer movieId, Integer seatNo){
        Seat seat = seatService.fetchSeatByMovieIdAndSeatNo(movieId, seatNo);
        StringBuilder sb = new StringBuilder();

        if(seat.isBooked()){
            sb.append("Seat is already booked");
        } else {
            seatService.bookSeat(seat);
            sb.append("Seat successfully booked");
            //Hardcoded QR generation
            sendLinqMsgWithMedia(chatId,
                    "https://img.freepik.com/premium-vector/qr-code-isolated-icon-transparent-background-vector-web_833641-1282.jpg?semt=ais_hybrid&w=740&q=80");
        }

        sendLinqMsg(chatId, sb.toString());
    }

    private void sendLinqMsgWithMedia(UUID chatId, String reqdLink){
        linqWebClient.post()
                .uri("chats/{chatId}/messages", chatId)
                .bodyValue(Map.of(
                        "message", Map.of(
                                "parts", List.of(
                                        Map.of(
                                                "type", "media",
                                                "url", reqdLink
                                        )
                                ),
                                "effect", Map.of(
                                        "type", "screen",
                                        "name", "sparkles"
                                )
                        )
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // makes it synchronous
    }

    private void displayAvailableSeats(UUID chatId, Integer movieId){
        List<Seat> listAvailableSeats = seatService.fetchAvailableSeats(movieId);

        StringBuilder sb = new StringBuilder();
        if(listAvailableSeats.size()==0){
            sb.append("No available seats");
        } else {
            for (Seat seat : listAvailableSeats) {
                sb.append(seat.getMovieId()+"#"+seat.getSeatNo())
                        .append("\n");
            }
            sb.append("\nSend Seat<space>Number to select seat");
        }

        sendLinqMsg(chatId, sb.toString());
    }

    private void displayAllMovies(UUID chatId){
        List<Movie> listMovies = movieService.fetchAllMovies();

        StringBuilder sb = new StringBuilder();
        for (Movie movie : listMovies) {
            sb.append(movie.getTitle())
                    .append(" ")
                    .append(movie.getId())
                    .append("\n");
        }
        sb.append("\nSend Movie<space>Number to select movie");

        sendLinqMsg(chatId, sb.toString());
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
//                                ,
//                                "effect", Map.of(
//                                        "type", "screen",
//                                        "name", "confetti"
//                                )
                        )
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // makes it synchronous
    }

    private List<LinqMessageResponse> fetchLinqMsgFromChat(UUID chatId, Integer limit){
        return linqWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("chats/{chatId}/messages")
                        .queryParam("limit", limit)
                        .build(chatId))
                .retrieve()
                .bodyToMono(LinqMessagesResponse.class)
                .block()
                .getMessages();
    }


    private LinqMessageResponse fetchLinqMsg(UUID msgId){
        return linqWebClient.get()
                .uri("messages/{messageId}", msgId)
                .retrieve()
                .bodyToMono(LinqMessageResponse.class)
                .block(); // makes it synchronous
    }
}
