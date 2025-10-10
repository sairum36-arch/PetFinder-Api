package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.IncidentMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentMessageRepository extends JpaRepository<IncidentMessageEntity, Long> {
}
