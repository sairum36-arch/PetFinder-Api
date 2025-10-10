package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.PetMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PetMediaRepository extends JpaRepository<PetMediaEntity, Long> {
    @Query("SELECT pm FROM PetMediaEntity pm WHERE pm.petEntity = :pet AND pm.isMainPhoto = :isMain")
    Optional<PetMediaEntity> findMainPhotoForPet(@Param("pet") PetEntity pet, @Param("isMain") boolean isMain);
}
