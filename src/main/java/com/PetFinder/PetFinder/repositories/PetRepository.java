package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

    @Query("SELECT p FROM PetEntity p " +
            "LEFT JOIN FETCH p.mediaFiles " +
            "LEFT JOIN FETCH p.collarEntity " +
            "WHERE p.id = :id")
    Optional<PetEntity> findPetWithMediaById(@Param("id") Long id);

    @Query("SELECT p FROM PetEntity p " +
            "LEFT JOIN FETCH p.breedEntity " +
            "LEFT JOIN FETCH p.collarEntity " +
            "LEFT JOIN FETCH p.mediaFiles " +
            "WHERE p.id = :id")
    Optional<PetEntity> findPetDetailsById(@Param("id") Long id);

    @Query("SELECT p FROM PetEntity p LEFT JOIN FETCH p.mediaFiles WHERE p IN :pets")
    List<PetEntity> findWithMedia(@Param("pets") List<PetEntity> pets);
}
