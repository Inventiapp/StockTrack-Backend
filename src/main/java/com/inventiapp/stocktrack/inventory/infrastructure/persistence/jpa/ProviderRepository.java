package com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for Provider entity.
 *
 * @summary
 * This interface extends JpaRepository to provide CRUD operations for the Provider aggregate.
 * It defines additional methods to search providers by email and RUC.
 *
 * @since 1.0
 */
@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    /**
     * Finds a provider by email.
     *
     * @param email The provider's email address.
     * @return Optional Provider.
     */
    Optional<Provider> findByEmail(String email);

    /**
     * Finds a provider by RUC.
     *
     * @param ruc The provider's RUC value.
     * @return Optional Provider.
     */
    Optional<Provider> findByRuc(String ruc);

    /**
     * Checks if a provider exists by email.
     *
     * @param email The provider's email address.
     * @return True if exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a provider exists by RUC.
     *
     * @param ruc The provider's RUC value.
     * @return True if exists, false otherwise.
     */
    boolean existsByRuc(String ruc);
}
