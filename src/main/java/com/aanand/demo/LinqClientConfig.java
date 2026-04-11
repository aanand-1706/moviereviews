package com.aanand.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class LinqClientConfig {
    @Value("${linqapi.token}")
    private String apiToken;

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.linqapp.com/api/partner/v3/")
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .build();
    }
}