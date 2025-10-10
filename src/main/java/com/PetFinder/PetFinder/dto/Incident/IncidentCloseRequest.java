package com.PetFinder.PetFinder.dto.Incident;


import com.PetFinder.PetFinder.entity.IncidentStatus;
import lombok.Data;

@Data
public class IncidentCloseRequest {
    private IncidentStatus finalStatus;
    private Long helperUserId;
}