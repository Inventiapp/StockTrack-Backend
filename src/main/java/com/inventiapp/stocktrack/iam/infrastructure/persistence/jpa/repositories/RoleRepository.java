package com.inventiapp.stocktrack.iam.infrastructure.persistence.jpa.repositories;

import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by name
     * @param name The role name
     * @return Optional Role
     */
    Optional<Role> findByName(Roles name);

    /**
     * Check if a role exists by name
     * @param name The role name
     * @return true if exists, false otherwise
     */
    boolean existsByName(Roles name);
}

