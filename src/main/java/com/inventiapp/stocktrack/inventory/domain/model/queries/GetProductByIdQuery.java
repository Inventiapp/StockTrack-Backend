package com.inventiapp.stocktrack.inventory.domain.model.queries;

/**
 * Query to get a product by id.
 *
 * @param productId Product id.
 */
public record GetProductByIdQuery(Long productId) {
    /**
     * Constructor validation.
     *
     * @param productId Product id. Must be greater than 0 and not null.
     * @throws IllegalArgumentException if productId is null or not greater than 0.
     */
    public GetProductByIdQuery {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId is required and must be greater than 0");
        }
    }
}
