package com.fitness.activityservice.service;

import com.fitness.activityservice.Exception.InvalidException;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor// only for final fields
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidation userValidation;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        boolean isValidUser = userValidation.validateUser(activityRequest.getUserId());
        if(!isValidUser){
            throw new InvalidException("Invalid User");
        }
        // convert to Activity
        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .type(activityRequest.getActivityType())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .createdAt(activityRequest.getStartTime())
                .build();
        Activity savedActivity = activityRepository.save(activity);
        //publish to rabbitmq for Ai service processing
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        }
        catch (Exception e){
            log.error("Error in RabbitMq to publish");
        }
        //convert to response

        return ActivityResponse.builder()
                                            .id(savedActivity.getId())
                                            .userId(savedActivity.getUserId())
                                            .additionalMetrics(savedActivity.getAdditionalMetrics())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .duration(savedActivity.getDuration())
                .creatAt(savedActivity.getCreatedAt())
                .startTime(savedActivity.getStartTime())
                .updateAt(savedActivity.getUpdatedAt())
                .build();
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities= activityRepository.findByUserId(userId);
        List<ActivityResponse> activityResponses = new ArrayList<>();
        for(Activity activity:activities){
            ActivityResponse activityResponse = ActivityResponse.builder()
                    .id(activity.getId())
                    .userId(activity.getUserId())
                    .additionalMetrics(activity.getAdditionalMetrics())
                    .caloriesBurned(activity.getCaloriesBurned())
                    .duration(activity.getDuration())
                    .creatAt(activity.getCreatedAt())
                    .startTime(activity.getStartTime())
                    .updateAt(activity.getUpdatedAt())
                    .build();
            activityResponses.add(activityResponse);
        }
        return activityResponses;
    }

    public ActivityResponse getActivity(String activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return ActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .additionalMetrics(activity.getAdditionalMetrics())
                .caloriesBurned(activity.getCaloriesBurned())
                .duration(activity.getDuration())
                .creatAt(activity.getCreatedAt())
                .startTime(activity.getStartTime())
                .updateAt(activity.getUpdatedAt())
                .build();
    }

}
