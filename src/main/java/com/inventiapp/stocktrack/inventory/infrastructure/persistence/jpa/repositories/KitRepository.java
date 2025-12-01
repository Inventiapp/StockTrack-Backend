package com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Kit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for Kit entity.
 * 
 * @summary
 * This interface extends JpaRepository to provide CRUD operations for Kit entity.
 * It extends Spring Data JpaRepository with Kit as the entity type and Long as the ID type.
 * @since 1.0
 */
@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {
    /**
     * Find a kit by name.
     * @param name The kit name
     * @return Optional Kit
     */
    Optional<Kit> findByName(String name);

    /**
     * Check if a kit exists by name.
     * @param name The kit name
     * @return True if exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Find a kit by id with items loaded.
     * @param id The kit id
     * @return Optional Kit with items loaded
     */
    @Query("SELECT DISTINCT k FROM Kit k LEFT JOIN FETCH k.items WHERE k.id = :id")
    Optional<Kit> findByIdWithItems(@Param("id") Long id);

    /**
     * Find all kits with items loaded.
     * @return List of kits with items loaded
     */
    @Query("SELECT DISTINCT k FROM Kit k LEFT JOIN FETCH k.items")
    List<Kit> findAllWithItems();
}

