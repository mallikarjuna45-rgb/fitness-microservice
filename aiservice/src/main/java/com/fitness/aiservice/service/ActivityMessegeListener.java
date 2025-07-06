package com.fitness.aiservice.service;

import com.fitness.aiservice.config.Activity;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessegeListener {
    private final ActivityAIService activityAiService;
    private final RecommendationRepository recommendationRepository;
    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity){
        log.info("Processing Activity"+activity.getId());
        log.info("Ai Response"+activityAiService.generateRecommendation(activity));
        recommendationRepository.save(activityAiService.generateRecommendation(activity));
    }
}
