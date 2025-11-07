package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.Incident.IncidentBriefResponse;
import com.PetFinder.PetFinder.dto.Incident.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.Incident.IncidentDetailResponse;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;

    @PostMapping
    public IncidentBriefResponse createIncident(@RequestBody IncidentCreateRequest dto, @AuthenticationPrincipal CredentialEntity currentUser){
       return incidentService.createIncident(currentUser, dto);
    }

    @GetMapping("/{incidentId}")
    public IncidentDetailResponse getIncidentById(@PathVariable Long incidentId) {
        return incidentService.getIncidentDetails(incidentId);
    }

    @GetMapping("/active")
    public List<IncidentBriefResponse> findAllActiveIncidents(@RequestParam(required = false) Double userLat, @RequestParam(required = false) Double userLon) {
        return incidentService.findAllActiveIncidents(userLat, userLon);
    }

    @PostMapping("/{incidentId}/respond")
    public void respondToIncident(@PathVariable Long incidentId, @AuthenticationPrincipal CredentialEntity currentUser){
        incidentService.respondToIncident(incidentId, currentUser);
    }

    @PostMapping("/{incidentId}/close")
    public void closeIncident(@PathVariable Long incidentId, @AuthenticationPrincipal CredentialEntity currentUser){
        incidentService.closeIncidentByOwner(incidentId, currentUser);
    }
    @PostMapping("/{incidentId}/cancel")
    public void cancelIncident(@PathVariable Long incidentId, @AuthenticationPrincipal CredentialEntity currentUser){
        incidentService.cancelIncident(incidentId, currentUser);
    }

    @PostMapping("/{incidentId}/resolve-by-helper")
    public void resolveIncidentByHelper(@PathVariable Long incidentId, @AuthenticationPrincipal CredentialEntity currentUser){
        incidentService.resolveIncidentByHelper(incidentId, currentUser);
    }

}
