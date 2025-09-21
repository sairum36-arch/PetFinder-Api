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
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final UserRepository userRepository;
    private final BreedRepository breedRepository;

    public PetWithCollarsStatus createPet(PetCreateRequest dto, UserDetails currentUser) {
        User owner = findUserByDetails(currentUser);

        //todo save pet only with breedId, breed isn't necessary
        Breed breed = breedRepository.findById(dto.getBreedId())
                .orElseThrow(() -> new EntityNotFoundException("Порода с таким id не найдена"));


        Pet newPet = petMapper.toPetFull(dto, owner, breed);
        return petMapper.toDto(petRepository.save(newPet));
    }

    //todo strange thing
    public List<PetWithCollarsStatus> getPetsForUserProfile(User user) {
        //List<Pet> petList = new ArrayList<>(user.getPets());
        return petMapper.toDtoList(user.getPets());
    }

    public PetDetailResponse getPetDetails(Long petId, UserDetails currentUser) {
        //todo don't ask User, put userId into UserDetails
        User owner = findUserByDetails(currentUser);

        Pet userPet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Животное с таким id не найдено"));
        if (userPet.getUser().getId().equals(owner.getId())) {
            return petMapper.toDtoDetail(userPet);
        } else {
            throw new AccessDeniedException("Учетные записи не совпадают");
        }
    }
    public void deletePet(Long petId, UserDetails currentUser) {
        //todo don't ask User, put userId into UserDetails
        User owner = findUserByDetails(currentUser);

        Pet userPet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Животное с таким id не найдено"));
        if (userPet.getUser().getId().equals(owner.getId())) {
            petRepository.deleteById(petId);
        } else {
            throw new AccessDeniedException("Не совпадают учетные записи");
        }
    }
    public PetDetailResponse updatePet(Long petId, UserDetails currentUser, PetUpdateRequest dto) {
        User owner = findUserByDetails(currentUser);

        Pet updatePet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Животное с таким id не найдено"));
        if (updatePet.getUser().getId().equals(owner.getId())) {

            petMapper.updatePetFromDto(dto, updatePet);
            Pet savedPet = petRepository.save(updatePet);

            return petMapper.toDtoDetail(savedPet);
        } else {
            throw new AccessDeniedException("Не совпадают учетные записи");
        }
    }
    private User findUserByDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userRepository.findByCredentialEmail(email).orElseThrow(() -> new EntityNotFoundException("Пользователь с email: " + email + " не найден."));
    }

    @Transactional(readOnly = true)
    public List<PetWithCollarsStatus> getAllPetsForCurrentUser(UserDetails currentUser) {
        //todo включи debug для sql логов сделай точку останова здесь
        User owner = findUserByDetails(currentUser);
        List list = owner.getPets();
        //todo и здесь, посмотри, выполеняется ли лишний запрос на прогрузку lazy, почитай про проблему hibernate n+1
        return petMapper.toDtoList(list);
    }

}