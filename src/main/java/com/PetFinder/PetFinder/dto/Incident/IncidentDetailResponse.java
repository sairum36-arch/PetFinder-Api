package com.PetFinder.PetFinder.dto.Incident;

import com.PetFinder.PetFinder.dto.CoordinateDto;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class IncidentDetailResponse {
    private final Long id;
    private IncidentStatus status;
    private BigDecimal reward;
    private String ownersNotes;
    private LocalDateTime startedAt;
    private CoordinateDto lastKnownLocation;
    private PetInfo petInfo;
    private UserInfo ownerInfo;
    private List<Long> helperIds;
}
