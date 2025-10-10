package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.GeoFence.GeoFenceResponse;
import com.PetFinder.PetFinder.dto.locationHistory.LocationPoint;
import com.PetFinder.PetFinder.dto.mainProfile.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.pet.PetCreateRequest;
import com.PetFinder.PetFinder.dto.pet.PetDetailResponse;
import com.PetFinder.PetFinder.dto.pet.PetUpdateRequest;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.service.GeoFenceService;
import com.PetFinder.PetFinder.service.LocationHistoryService;
import com.PetFinder.PetFinder.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    private final GeoFenceService geoFenceService;
    private final LocationHistoryService locationHistoryService;

    @GetMapping("/my")
    public List<PetWithCollarsStatus> getAllPets(@AuthenticationPrincipal CredentialEntity currentUser){
        return petService.getAllPetsForCurrentUser(currentUser);
    }

    @PostMapping
    public void createPet(@RequestBody PetCreateRequest dto, @AuthenticationPrincipal CredentialEntity currentUser) {
        petService.createPet(dto, currentUser);
    }

    @GetMapping("/{petId}")
    public PetDetailResponse getPetDetails(@PathVariable Long petId, @AuthenticationPrincipal CredentialEntity currentUser) {
        return petService.getPetDetails(petId, currentUser);
    }
    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable Long petId, @AuthenticationPrincipal CredentialEntity currentUser) {
        petService.deletePet(petId, currentUser);
    }

    @PutMapping("/{petId}")
    public PetDetailResponse updatePet(
            @PathVariable Long petId,
            @AuthenticationPrincipal CredentialEntity currentUser,
            @RequestBody PetUpdateRequest dto
    ) {
        return petService.updatePet(petId, currentUser, dto);
    }

    @GetMapping("/{petId}/geofences")
    public List<GeoFenceResponse> getGeoFencesForPet(@PathVariable Long petId, @AuthenticationPrincipal CredentialEntity currentUser){
        return geoFenceService.getGeoFencesForPet(petId, currentUser);
    }

    @GetMapping("/{petId}/history")
    public List<LocationPoint> getPetLocationHistory(
            @PathVariable Long petId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @AuthenticationPrincipal CredentialEntity currentUser) {

        return locationHistoryService.getHistoryForPet(petId, start, end, currentUser);
    }
}