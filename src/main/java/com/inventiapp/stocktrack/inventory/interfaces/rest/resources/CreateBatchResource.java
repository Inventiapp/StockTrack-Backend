package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import java.util.Date;

/**
 * Resource used to create a Batch.
 * Validates the incoming payload at construction time.
 *
 * @param productId product id (required)
 * @param quantity quantity (required)
 * @param expirationDate expiration date (required)
 * @param receptionDate reception date (required)
 */
public record CreateBatchResource(
        Long productId,
        Integer quantity,
        Date expirationDate,
        Date receptionDate
) {
    /**
     * Validate the resource.
     *
     * @throws IllegalArgumentException if any required field is null or invalid.
     */
    public CreateBatchResource {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId cannot be null or less than or equal to 0");
        }
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be null or negative");
        }
        if (expirationDate == null) {
            throw new IllegalArgumentException("expirationDate cannot be null");
        }
        if (receptionDate == null) {
            throw new IllegalArgumentException("receptionDate cannot be null");
        }
    }
}
