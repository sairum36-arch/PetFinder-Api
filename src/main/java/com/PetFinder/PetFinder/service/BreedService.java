package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Breed;
import com.PetFinder.PetFinder.mapper.BreedMapper;
import com.PetFinder.PetFinder.repositories.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreedService {
    private final BreedRepository breedRepository;
    private final BreedMapper breedMapper;

    @Transactional(readOnly = true)
    public List<Breed> findAll(){
        return breedRepository.findAll().stream().map(breedMapper::toDto).collect(Collectors.toList());
    }
}
