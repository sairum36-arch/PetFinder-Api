package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.Incident;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import com.PetFinder.PetFinder.entity.Pet;
import com.PetFinder.PetFinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.awt.*;

public interface IncidentRepository extends JpaRepository<Incident, Long> {


    boolean existsByPetAndStatus(Pet pet, IncidentStatus incidentStatus);
}

