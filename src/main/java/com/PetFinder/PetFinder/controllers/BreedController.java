package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.Breed;
import com.PetFinder.PetFinder.service.BreedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/breeds")
public class BreedController {

    private final BreedService breedService;

    @GetMapping
    public List<Breed> getAllBreeds(){
        return breedService.findAll();
    }
}
