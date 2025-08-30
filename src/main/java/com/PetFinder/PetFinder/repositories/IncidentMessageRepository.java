package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.IncidentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentMessageRepository extends JpaRepository<IncidentMessage, Long> {
}
