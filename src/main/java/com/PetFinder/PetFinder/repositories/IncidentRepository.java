package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

}
