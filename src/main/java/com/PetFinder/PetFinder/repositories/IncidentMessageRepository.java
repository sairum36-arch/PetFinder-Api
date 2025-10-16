package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidentMessageRepository extends JpaRepository<IncidentMessageEntity, Long> {
    @Query("SELECT m FROM IncidentMessageEntity m WHERE m.incidentEntity = :incident ORDER BY m.sentAt ASC")
    List<IncidentMessageEntity> findByIncidentEntityOrderBySentAt(@Param("incident") IncidentEntity incident);
}
