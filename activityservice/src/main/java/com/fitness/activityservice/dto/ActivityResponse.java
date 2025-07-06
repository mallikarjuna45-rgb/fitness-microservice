package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.ActivityType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder

public class ActivityResponse {

    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private LocalDateTime startTime;
    private Integer caloriesBurned;
    private Map<String,Object> additionalMetrics;
    private LocalDateTime creatAt;
    private LocalDateTime updateAt;
}