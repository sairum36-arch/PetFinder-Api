package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.petDTOS.PetCreateRequest;
import com.PetFinder.PetFinder.dto.petDTOS.PetDetailResponse;
import com.PetFinder.PetFinder.dto.petDTOS.PetUpdateRequest;
import com.PetFinder.PetFinder.securityConfig.CustomUserDetails;
import com.PetFinder.PetFinder.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    @PostMapping("/create")
    public void createPet(@RequestBody PetCreateRequest dto , @AuthenticationPrincipal CustomUserDetails currentUser){
        petService.createPet(dto, currentUser);
    }
    @GetMapping("/{petId}")
    public PetDetailResponse getPetDetails(@PathVariable Long petId, @AuthenticationPrincipal CustomUserDetails currentUser){
        return petService.getPetDetails(petId, currentUser);
    }
    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable Long petId, @AuthenticationPrincipal CustomUserDetails currentUser){
        petService.deletePet(petId, currentUser);
    }
    @PutMapping("/{petId}")
    public PetDetailResponse updatePet(@PathVariable Long petId, @AuthenticationPrincipal CustomUserDetails currentUser, @RequestBody PetUpdateRequest dto){
        return petService.updatePet(petId, currentUser, dto);
    }

}
