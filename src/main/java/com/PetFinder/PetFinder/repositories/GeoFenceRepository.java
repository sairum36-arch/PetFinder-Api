package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.GeoFence;
import com.PetFinder.PetFinder.entity.Pet;
import com.PetFinder.PetFinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeoFenceRepository extends JpaRepository<GeoFence, Long> {

    List<GeoFence> findByPet_User(User user);
    List<GeoFence> findByPet(Pet pet);
}
