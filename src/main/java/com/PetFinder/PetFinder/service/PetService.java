package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.mainProfile.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.pet.PetCreateRequest;
import com.PetFinder.PetFinder.dto.pet.PetDetailResponse;
import com.PetFinder.PetFinder.dto.pet.PetUpdateRequest;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.PetMapper;
import com.PetFinder.PetFinder.repositories.BreedRepository;
import com.PetFinder.PetFinder.repositories.PetMediaRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import com.PetFinder.PetFinder.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final BreedRepository breedRepository;
    private final PetMediaRepository petMediaRepository;
    private static final Logger log = LoggerFactory.getLogger(PetService.class);
    private final FileStorageService fileStorageService;

    @Transactional
    public PetDetailResponse createPet(PetCreateRequest dto, CredentialEntity currentUser) {
        UserEntity owner = currentUser.getUserEntity();
        PetEntity newPetEntity = petMapper.toPetBase(dto);
        newPetEntity.setUserEntity(owner);
        if (dto.getBreedId() != null) {
            newPetEntity.setBreedEntity(breedRepository.getReferenceById(dto.getBreedId()));
        }
        PetEntity savedPet = petRepository.save(newPetEntity);
        if (StringUtils.hasText(dto.getMainPhotoKey())) {
            PetMediaEntity mainPhoto = new PetMediaEntity();
            mainPhoto.setPetEntity(savedPet);
            mainPhoto.setUrl(dto.getMainPhotoKey());
            mainPhoto.setMainPhoto(true);
            petMediaRepository.save(mainPhoto);
            if (savedPet.getMediaFiles() == null) {
                savedPet.setMediaFiles(new java.util.ArrayList<>());
            }
            savedPet.getMediaFiles().add(mainPhoto);
        }
        return enrichPetDetailResponse(petMapper.toDtoDetail(savedPet), savedPet);
    }

    @Transactional(readOnly = true)
    public PetDetailResponse getPetDetails(Long petId, CredentialEntity currentUser) {
        PetEntity userPetEntity = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Животное с таким id не найдено"));
        if (!userPetEntity.getUserEntity().getId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Учетные записи не совпадают");
        }
        PetDetailResponse responseDto = petMapper.toDtoDetail(userPetEntity);
        return enrichPetDetailResponse(responseDto, userPetEntity);
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
        PetDetailResponse responseDto = petMapper.toDtoDetail(petToUpdate);
        return enrichPetDetailResponse(responseDto, petToUpdate);
    }

    @Transactional(readOnly = true)
    public List<PetWithCollarsStatus> getAllPetsForCurrentUser(CredentialEntity currentUser) {
        UserEntity owner = currentUser.getUserEntity();
        List<PetWithCollarsStatus> dtoList = petMapper.toDtoList(owner.getPetEntities());
        dtoList.forEach(dto -> {
            PetEntity correspondingEntity = owner.getPetEntities().stream()
                    .filter(entity -> entity.getId().equals(dto.getPetId()))
                    .findFirst().orElse(null);

            if (correspondingEntity != null) {
                enrichPetStatusResponse(dto, correspondingEntity);
            }
        });

        return dtoList;
    }

    public PetDetailResponse enrichPetDetailResponse(PetDetailResponse dto, PetEntity entity) {
        getPhotoUrlFromEntity(entity).ifPresent(dto::setPetMainPhotoUrl);
        return dto;
    }

    public PetWithCollarsStatus enrichPetStatusResponse(PetWithCollarsStatus dto, PetEntity entity) {
        getPhotoUrlFromEntity(entity).ifPresent(dto::setPetMainPhotoUrl);
        return dto;
    }

    private Optional<String> getPhotoUrlFromEntity(PetEntity entity) {
        if (entity.getMediaFiles() == null || entity.getMediaFiles().isEmpty()) {
            return Optional.empty();
        }
        return entity.getMediaFiles().stream()
                .filter(PetMediaEntity::isMainPhoto)
                .findFirst()
                .map(media -> fileStorageService.getObjectUrl(media.getUrl()));
    }
}