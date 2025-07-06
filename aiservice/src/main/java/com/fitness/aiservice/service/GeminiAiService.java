package com.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class GeminiAiService {

    @Value("${gemini.api.url}")
    private String geminiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    private final WebClient webClient;
    public GeminiAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    //constructor injection
    //promt
    public String getAnswer(String question) {
        Map<String,Object> requestBody =
                Map.of("contents" , new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",question)
                        })
                });

        //making sychronous communicatio with direct url(geminiApi) without use of any other service
        String response = webClient.post()
                .uri(geminiUrl+geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
