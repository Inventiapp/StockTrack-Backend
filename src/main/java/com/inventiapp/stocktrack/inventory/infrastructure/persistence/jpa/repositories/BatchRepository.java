package com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for Batch aggregate.
 * Provides basic CRUD operations and additional query methods if needed.   
 */
@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    /**
     * Optional: check if a batch exists by productId
     * Useful to enforce uniqueness
     * @param productId product id
     * @return true if exists
     */
    boolean existsByProductId(Long productId);
    /**
     * Optional: check if a batch exists by expirationDate
     * Useful to enforce uniqueness
     * @param expirationDate expiration date
     * @return true if exists
     */
    boolean existsByExpirationDate(Date expirationDate);
    /**
     * Optional: check if a batch exists by receptionDate
     * Useful to enforce uniqueness
     * @param receptionDate reception date
     * @return true if exists
     */
    boolean existsByReceptionDate(Date receptionDate);

    List<Batch> findByProductIdOrderByExpirationDateAsc(Long productId);
}
