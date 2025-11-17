package com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product aggregate.
 * Provides basic CRUD operations and additional query methods if needed.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Optional: check if a product exists by name and providerId
     * Useful to enforce uniqueness
     * @param name product name
     * @param providerId provider id
     * @return true if exists
     */
    boolean existsByNameAndProviderId(String name, String providerId);

}
