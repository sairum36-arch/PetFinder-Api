package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentDetailResponse;
import com.PetFinder.PetFinder.securityConfig.CustomUserDetails;
import com.PetFinder.PetFinder.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    @PostMapping("/create")
    public void createIncident(@RequestBody IncidentCreateRequest dto, @AuthenticationPrincipal CustomUserDetails currentUser){
        incidentService.createIncident(dto, currentUser);
    }
    @GetMapping("/{incidentId}")
    public IncidentDetailResponse getIncidentById(@PathVariable Long incidentId) {
        return incidentService.getIncidentDetails(incidentId);
    }
}
