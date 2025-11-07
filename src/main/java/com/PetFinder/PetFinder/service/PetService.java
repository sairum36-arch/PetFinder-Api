package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.mainProfile.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.pet.PetCreateRequest;
import com.PetFinder.PetFinder.dto.pet.PetDetailResponse;
import com.PetFinder.PetFinder.dto.pet.PetUpdateRequest;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.exception.ResourceConflictException;
import com.PetFinder.PetFinder.mapper.PetMapper;
import com.PetFinder.PetFinder.repositories.BreedRepository;
import com.PetFinder.PetFinder.repositories.CollarRepository;
import com.PetFinder.PetFinder.repositories.PetMediaRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final BreedRepository breedRepository;
    private final PetMediaRepository petMediaRepository;
    private final CollarRepository collarRepository;


    @Transactional
    public PetDetailResponse createPet(PetCreateRequest dto, CredentialEntity currentUser){
        UserEntity user = currentUser.getUserEntity();
        PetEntity newPetEntity = petMapper.toPetBase(dto);
        newPetEntity.setUserEntity(user);
        if (dto.getBreedId() != null) {
            BreedEntity breed = breedRepository.findById(dto.getBreedId())
                    .orElseThrow(() -> new EntityNotFoundException("Порода с ID " + dto.getBreedId() + " не найдена."));
            newPetEntity.setBreedEntity(breed);
        }
        PetEntity savedPet = petRepository.save(newPetEntity);
        if (StringUtils.hasText(dto.getDeviceId())) {
            CollarEntity collar = collarRepository.findByDeviceId(dto.getDeviceId())
                    .orElseGet(() -> {
                        CollarEntity newCollar = new CollarEntity();
                        newCollar.setDeviceId(dto.getDeviceId());
                        newCollar.setStatus(CollarStatus.UNPAIRED);
                        return newCollar;
                    });

            if (collar.getPetEntity() != null) {
                throw new ResourceConflictException("Ошейник с ID " + dto.getDeviceId() + " уже используется.");
            }
            collar.setPetEntity(savedPet);
            collar.setStatus(CollarStatus.ACTIVE);
            collarRepository.save(collar);
            savedPet.setCollarEntity(collar);
        }

        if (StringUtils.hasText(dto.getMainPhotoKey())) {
            PetMediaEntity mainPhoto = new PetMediaEntity();
            mainPhoto.setPetEntity(savedPet);
            mainPhoto.setUrl(dto.getMainPhotoKey());
            mainPhoto.setMainPhoto(true);
            petMediaRepository.save(mainPhoto);
            if (savedPet.getMediaFiles() == null) {
                savedPet.setMediaFiles(new ArrayList<>());
            }
            savedPet.getMediaFiles().add(mainPhoto);
        }
        return petMapper.toDtoDetail(savedPet);
    }

    @Transactional(readOnly = true)
    public PetDetailResponse getPetDetails(Long petId, CredentialEntity currentUser) {
        PetEntity userPetEntity = petRepository.findPetDetailsById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Животное с таким id не найдено"));
        if (!userPetEntity.getUserEntity().getId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Учетные записи не совпадают");
        }
        return petMapper.toDtoDetail(userPetEntity);
    }

    @Transactional
    public void deletePet(Long petId, CredentialEntity currentUser) {
        Long currentUserId = currentUser.getUserId();
        PetEntity petToDelete = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Животное с таким id не найдено"));
        if (petToDelete.getUserEntity().getId().equals(currentUserId)) {
            petRepository.delete(petToDelete);
        } else {
            throw new AccessDeniedException("Не совпадают учетные записи");
        }
    }

    @Transactional
    public PetDetailResponse updatePet(Long petId, CredentialEntity currentUser, PetUpdateRequest dto) {
        PetEntity petToUpdate = petRepository.findPetWithMediaById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Питомец с ID " + petId + " не найден."));
        if (!petToUpdate.getUserEntity().getId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("У вас нет прав для редактирования этого питомца.");
        }
        petMapper.updatePetFromDto(dto, petToUpdate);
        CollarEntity currentCollar = petToUpdate.getCollarEntity();
        String newDeviceId = dto.getDeviceId();

        if ( currentCollar != null && !StringUtils.hasText(newDeviceId) ) {
            currentCollar.setPetEntity(null);
            currentCollar.setStatus(CollarStatus.UNPAIRED);
            petToUpdate.setCollarEntity(null);
        }
        else if (StringUtils.hasText(newDeviceId)) {
            if (currentCollar == null || !currentCollar.getDeviceId().equals(newDeviceId)) {
                if (currentCollar != null) {
                    currentCollar.setPetEntity(null);
                    currentCollar.setStatus(CollarStatus.UNPAIRED);
                }
                CollarEntity newCollar = collarRepository.findByDeviceId(newDeviceId).orElseGet(() -> {
                    CollarEntity c = new CollarEntity();
                    c.setDeviceId(newDeviceId);
                    c.setStatus(CollarStatus.UNPAIRED);
                    return c;
                });
                if (newCollar.getPetEntity() != null) {
                    throw new ResourceConflictException("Ошейник с ID " + newDeviceId + " уже используется.");
                }
                newCollar.setPetEntity(petToUpdate);
                newCollar.setStatus(CollarStatus.ACTIVE);
                petToUpdate.setCollarEntity(collarRepository.save(newCollar));
            }
        }
        if (StringUtils.hasText(dto.getMainPhotoKey())) {
            Optional<PetMediaEntity> oldMainPhotoOpt = petMediaRepository.findMainPhotoForPet(petToUpdate, true);
            if (oldMainPhotoOpt.isPresent()) {
                oldMainPhotoOpt.get().setUrl(dto.getMainPhotoKey());
            } else {
                PetMediaEntity newMainPhoto = new PetMediaEntity();
                newMainPhoto.setPetEntity(petToUpdate);
                newMainPhoto.setUrl(dto.getMainPhotoKey());
                newMainPhoto.setMainPhoto(true);
                petMediaRepository.save(newMainPhoto);
                petToUpdate.getMediaFiles().add(newMainPhoto);
            }
        }
        return petMapper.toDtoDetail(petToUpdate);
    }

    @Transactional(readOnly = true)
    public List<PetWithCollarsStatus> getAllPetsForCurrentUser(CredentialEntity currentUser) {
        UserEntity owner = currentUser.getUserEntity();
        return petMapper.toDtoList(owner.getPetEntities());
    }


}