package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
    @Query("SELECT COUNT(i) > 0 FROM IncidentEntity i WHERE i.petEntity = :pet AND i.status = :status")
    boolean existsByPetAndStatus(@Param("pet") PetEntity petEntity, @Param("status") IncidentStatus incidentStatus);

    @Query("SELECT i FROM IncidentEntity i WHERE i.status = :status")
    List<IncidentEntity> findAllByStatus(@Param("status") IncidentStatus incidentStatus);

    @Query("SELECT i FROM IncidentEntity i WHERE i.petEntity.userEntity = :owner")
    List<IncidentEntity> findAllByPetOwner(@Param("owner") UserEntity owner);

    @Query("SELECT i FROM IncidentEntity i " +
            "LEFT JOIN FETCH i.petEntity pe " +
            "LEFT JOIN FETCH pe.userEntity " + "LEFT JOIN FETCH i.responses r " +
            "LEFT JOIN FETCH r.helperUserEntity " + "WHERE i.id = :id")
    Optional<IncidentEntity> findByIdWithDetails(@Param("id") Long id);
}

