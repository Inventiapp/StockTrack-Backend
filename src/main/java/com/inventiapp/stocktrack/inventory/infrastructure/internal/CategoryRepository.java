package com.inventiapp.stocktrack.inventory.infrastructure.internal;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for Category entity.
 * @summary
 * This interface extends JpaRepository to provide CRUD operations for Category entity.
 * It extends Spring Data JpaRepository with Category as the entity type and Long as the ID type.
 * @since 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Find a category by name.
     * @param name The category name
     * @return Optional Category
     */
    Optional<Category> findByName(String name);

    /**
     * Check if a category exists by name.
     * @param name The category name
     * @return True if exists, false otherwise
     */
    boolean existsByName(String name);
}



