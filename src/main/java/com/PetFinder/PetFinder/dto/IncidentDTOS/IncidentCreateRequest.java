package com.PetFinder.PetFinder.dto.IncidentDTOS;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IncidentCreateRequest {
    private Long petId;
    private String ownerNotes;
    private BigDecimal reward;

}
