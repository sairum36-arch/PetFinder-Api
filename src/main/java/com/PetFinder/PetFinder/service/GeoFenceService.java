package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.GeoFence.GeoFenceResponse;
import com.PetFinder.PetFinder.dto.GeoFence.GeofenceCreateRequest;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.GeoFenceEntity;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.GeoFenceMapper;
import com.PetFinder.PetFinder.repositories.GeoFenceRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GeoFenceService {
    private final GeoFenceRepository geoFenceRepository;
    private final GeoFenceMapper geoFenceMapper;
    private final PetRepository petRepository;

    @Transactional
    public GeoFenceResponse createGeoFence(GeofenceCreateRequest request, CredentialEntity currentUser) {
        PetEntity pet = petRepository.findById(request.getPetId()).orElseThrow(() -> new EntityNotFoundException("Питомец с id " + request.getPetId() + " не найден"));
        if (!pet.getUserEntity().getId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("доступ запрещен к чужому питомцу");
        }
        GeoFenceEntity newGeoFence = new GeoFenceEntity();
        newGeoFence.setName(request.getName());
        newGeoFence.setPetEntity(pet);
        newGeoFence.setArea(geoFenceMapper.coordinateDtoListToPolygon(request.getPoints()));
        GeoFenceEntity savedGeoFence = geoFenceRepository.save(newGeoFence);
        return geoFenceMapper.toDTO(savedGeoFence);
    }
    @Transactional(readOnly = true)
    public List<GeoFenceResponse> getGeoFencesForPet(Long petId, CredentialEntity currentUser){
        List<GeoFenceEntity> geoFences = geoFenceRepository.findAllByPetIdAndUserId(petId, currentUser.getUserId());
        return geoFenceMapper.toDTOList(geoFences);
    }
    public void deleteGeofence(Long id, CredentialEntity currentUser){
        GeoFenceEntity geoFence = geoFenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("геозона с id" +  id + " не найдена"));
        if(!geoFence.getPetEntity().getUserEntity().getId().equals(currentUser.getUserId())){
            throw new AccessDeniedException("у вас нет прав доступа для удаления этой геозоны");
        }
        geoFenceRepository.delete(geoFence);
    }

}
