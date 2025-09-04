package com.PetFinder.PetFinder.dto.IncidentDTOS;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IncidentBriefResponse {
    private Long id;
    private PetBriefInfoDto pet;
    private BigDecimal reward;
    private LocalDateTime startedAt;
    private Double distanceToUserKm;
}