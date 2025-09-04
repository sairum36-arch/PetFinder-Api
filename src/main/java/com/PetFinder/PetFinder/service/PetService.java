package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.mainProfileDTOS.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.petDTOS.PetCreateRequest;
import com.PetFinder.PetFinder.dto.petDTOS.PetDetailResponse;
import com.PetFinder.PetFinder.dto.petDTOS.PetUpdateRequest;
import com.PetFinder.PetFinder.entity.Breed;
import com.PetFinder.PetFinder.entity.Pet;
import com.PetFinder.PetFinder.entity.User;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.PetMapper;
import com.PetFinder.PetFinder.repositories.BreedRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import com.PetFinder.PetFinder.repositories.UserRepository;
import com.PetFinder.PetFinder.securityConfig.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PetService {
    private  final PetRepository petRepository;
    private final PetMapper petMapper;
    private final UserRepository userRepository;
    private final BreedRepository breedRepository;

    public PetWithCollarsStatus createPet(PetCreateRequest dto, CustomUserDetails currentUser){
        User owner = userRepository.findById(currentUser.getId()).orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден"));
        Breed breed = breedRepository.findById(dto.getBreedId()).orElseThrow(() -> new EntityNotFoundException("порода с таким id не найдена"));
        Pet newPet = petMapper.toPetFull(dto, owner, breed);
        return petMapper.toDto(petRepository.save(newPet));
    }
    public List<PetWithCollarsStatus> getPetsForUserProfile(User user){
        List<Pet> petList = new ArrayList<>(user.getPets());
        return petMapper.toDtoList(petList);

    }
    public PetDetailResponse getPetDetails(Long petId, CustomUserDetails currentUser){
        Pet userPet  = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("животное с таким id не найдено"));
        if(userPet.getUser().getId().equals(currentUser.getId())) {
            return petMapper.toDtoDetail(userPet);
        }else{
            throw new AccessDeniedException("учетные записи не совпадают");
        }
    }
    public void deletePet(Long petId, CustomUserDetails currentUser){
        Pet userPet = petRepository.findById(petId).orElseThrow(() ->  new EntityNotFoundException("животное с таким id не найдено"));
        if(userPet.getUser().getId().equals(currentUser.getId())){
            petRepository.deleteById(petId);
        }else{
            throw new AccessDeniedException("не совпадают учетные записи");
        }
    }
    public PetDetailResponse updatePet(Long petId, CustomUserDetails currentUser, PetUpdateRequest dto){
        Pet updatePet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("животное с таким id не найдено"));
        if(updatePet.getUser().getId().equals(currentUser.getId())){
            petMapper.updatePetFromDto(dto, updatePet);
            Pet savedPet = petRepository.save(updatePet);
            return petMapper.toDtoDetail(savedPet);
        }else{
            throw new AccessDeniedException("не совпадают учетные записи");
        }
    }
}
