package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.GeoFence.GeoFenceResponse;
import com.PetFinder.PetFinder.dto.GeoFence.GeofenceCreateRequest;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.repositories.GeoFenceRepository;
import com.PetFinder.PetFinder.service.GeoFenceService;
import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.Path;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/geofences")
public class GeoFenceController {
    private final GeoFenceService geoFenceService;
    @PostMapping
    public GeoFenceResponse createGeoFence(@RequestBody GeofenceCreateRequest request, @AuthenticationPrincipal CredentialEntity currentUser){
        GeoFenceResponse createdGeoFence = geoFenceService.createGeoFence(request, currentUser);
        return createdGeoFence;
    }
    @DeleteMapping("/{id}")
    public void deleteGeofence(@PathVariable Long id, @AuthenticationPrincipal CredentialEntity currentUser){
        geoFenceService.deleteGeofence(id, currentUser);
    }

}
