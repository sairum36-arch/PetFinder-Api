package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncidentParticipationRepository extends JpaRepository<IncidentParticipationEntity, Long> {
    @Query("SELECT COUNT(ip) > 0 FROM IncidentParticipationEntity ip " +
            "WHERE ip.incidentEntity = :incident AND ip.helperUserEntity = :helper")
    boolean existsByIncidentEntityAndHelperUserEntity(@Param("incident") IncidentEntity incident, @Param("helper") UserEntity helper);
}
