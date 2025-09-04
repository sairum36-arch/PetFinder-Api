package com.PetFinder.PetFinder.dto.IncidentDTOS;

import com.PetFinder.PetFinder.dto.GeoFenceDTOS.CoordinateDto;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import lombok.Data;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IncidentDetailResponse {
    private final Long id;
    private IncidentStatus status;
    private BigDecimal reward;
    private String ownersNotes;
    private LocalDateTime startedAt;
    private CoordinateDto lastKnownLocation;
    private PetInfoDto petInfo;
    private UserInfoDto ownerInfo;
}
