package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.BreedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<BreedEntity, Long> {
}
