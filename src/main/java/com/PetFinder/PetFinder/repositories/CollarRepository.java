package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.CollarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface CollarRepository extends JpaRepository<CollarEntity, Long> {
    Optional<CollarEntity> findByDeviceId(String deviceId);

    //todo jpql (сделано)
    @Query("SELECT c FROM CollarEntity c WHERE c.deviceId =:deviceId AND c.petEntity.id = :petId")
    Optional<CollarEntity> findByDeviceIdAndPet(@Param("deviceId") String deviceId,@Param("petId") Long petId);
}
