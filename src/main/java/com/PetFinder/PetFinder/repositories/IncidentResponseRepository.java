package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentResponseRepository extends JpaRepository<IncidentResponse, Long> {

    boolean existsByIncidentAndHelperUser(Incident incident, User helperUser);
}
