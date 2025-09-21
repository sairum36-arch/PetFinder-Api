package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.mainProfileDTOS.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.petDTOS.PetCreateRequest;
import com.PetFinder.PetFinder.dto.petDTOS.PetDetailResponse;
import com.PetFinder.PetFinder.dto.petDTOS.PetUpdateRequest;
import com.PetFinder.PetFinder.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/my")
    public List<PetWithCollarsStatus> getAllPets(@AuthenticationPrincipal UserDetails currentUser){
        return petService.getAllPetsForCurrentUser(currentUser);
    }

    //todo убрать урлу
    @PostMapping("/create")
    public void createPet(@RequestBody PetCreateRequest dto, @AuthenticationPrincipal UserDetails currentUser) {
        petService.createPet(dto, currentUser);
    }
    @GetMapping("/{petId}")
    public PetDetailResponse getPetDetails(@PathVariable Long petId, @AuthenticationPrincipal UserDetails currentUser) {
        return petService.getPetDetails(petId, currentUser);
    }
    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable Long petId, @AuthenticationPrincipal UserDetails currentUser) {
        petService.deletePet(petId, currentUser);
    }

    @PutMapping("/{petId}")
    public PetDetailResponse updatePet(
            @PathVariable Long petId,
            @AuthenticationPrincipal UserDetails currentUser,
            @RequestBody PetUpdateRequest dto
    ) {
        return petService.updatePet(petId, currentUser, dto);
    }
}