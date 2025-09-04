package com.PetFinder.PetFinder.dto.IncidentDTOS;


import com.PetFinder.PetFinder.entity.IncidentStatus;
import lombok.Data;

@Data
public class IncidentCloseRequest {
    private IncidentStatus finalStatus;
    private Long helperUserId;
}