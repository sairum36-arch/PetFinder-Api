package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.GeoFenceEntity;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeoFenceRepository extends JpaRepository<GeoFenceEntity, Long> {
    @Query("SELECT gf FROM GeoFenceEntity gf " + "WHERE gf.petEntity.id = :petId AND gf.petEntity.userEntity.id = :userId")
    List<GeoFenceEntity> findAllByPetIdAndUserId(@Param("petId") Long petId, @Param("userId") Long userId);
}
