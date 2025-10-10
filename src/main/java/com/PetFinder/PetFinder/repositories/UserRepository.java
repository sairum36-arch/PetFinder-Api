package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.credentialEntity.email = :email")
    Optional<UserEntity> findByEmail(@Param("email")String email);
    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.credentialEntity " +
            "LEFT JOIN FETCH u.petEntities p " +
            "LEFT JOIN FETCH p.breedEntity " +
            "LEFT JOIN FETCH p.collarEntity " +
            "WHERE u.credentialEntity.email = :email")
    Optional<UserEntity> findUserDetailsByEmail(@Param("email") String email);
}

