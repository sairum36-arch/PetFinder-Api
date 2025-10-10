package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
    @Query("SELECT c FROM CredentialEntity c WHERE c.email = :email")
    Optional<CredentialEntity> findByEmail(@Param("email")String email);

    @Query("SELECT COUNT(c) > 0 FROM CredentialEntity c WHERE c.email = :email" )
    boolean existsByEmail(@Param("email")String email);
}
