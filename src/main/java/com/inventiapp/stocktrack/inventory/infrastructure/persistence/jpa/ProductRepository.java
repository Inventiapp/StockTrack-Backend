package com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Product aggregate.
 * Provides basic CRUD operations and additional query methods if needed.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Optional: find all products by provider id.
     * @param providerId the provider id
     * @return list of products belonging to the given provider
     */
    List<Product> findByProviderId(String providerId);

    /**
     * Optional: find all products by category id.
     * @param categoryId the category id
     * @return list of products belonging to the given category
     */
    List<Product> findByCategoryId(String categoryId);

    /**
     * Optional: check if a product exists by name and providerId
     * Useful to enforce uniqueness
     * @param name product name
     * @param providerId provider id
     * @return true if exists
     */
    boolean existsByNameAndProviderId(String name, String providerId);
}
