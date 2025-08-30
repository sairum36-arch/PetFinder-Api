package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByEmail(String email);
    boolean existsByEmail(String email);
}
