package com.inventiapp.stocktrack.iam.infrastructure.persistence.jpa.repositories;

import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User aggregate
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email
     * @param email The email
     * @return Optional User
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists by email
     * @param email The email
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}

