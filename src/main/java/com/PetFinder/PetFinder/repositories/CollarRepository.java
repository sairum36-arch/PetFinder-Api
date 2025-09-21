package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.Collar;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CollarRepository extends JpaRepository<Collar, Long> {
    Optional<Collar> findByDeviceId(String deviceId);

    //todo jpql
    Optional<Collar> findByDeviceIdAndPet_Id(String deviceId, Long petId);
}
