package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import com.PetFinder.PetFinder.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
    @Query("SELECT COUNT(i) > 0 FROM IncidentEntity i WHERE i.petEntity = :pet AND i.status = :status")
    boolean existsByPetAndStatus(@Param("pet") PetEntity petEntity, @Param("status") IncidentStatus incidentStatus);

    @Query("SELECT i FROM IncidentEntity i WHERE i.status = :status")
    List<IncidentEntity> findAllByStatus(@Param("status") IncidentStatus incidentStatus);
}

