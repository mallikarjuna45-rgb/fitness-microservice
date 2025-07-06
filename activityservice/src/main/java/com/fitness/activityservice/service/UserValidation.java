package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidation {
    private final WebClient userServiceWebClient;
    public boolean validateUser(String userId){
        try{
            log.info("Validating User");
        return Boolean.TRUE.equals(userServiceWebClient.get()
                .uri("/api/users/{userId}/validation", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
        } catch (WebClientResponseException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND)
            throw new RuntimeException(e);
            else if ((e.getStatusCode() == HttpStatus.BAD_REQUEST)){
                throw new RuntimeException("Ivalid Request");
            }
        }
        return false;
    }
}
