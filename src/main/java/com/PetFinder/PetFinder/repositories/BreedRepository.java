package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}
