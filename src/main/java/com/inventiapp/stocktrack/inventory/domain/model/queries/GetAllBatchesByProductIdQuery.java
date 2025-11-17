package com.inventiapp.stocktrack.inventory.domain.model.queries;

public record GetAllBatchesByProductIdQuery(Long productId) {

    public GetAllBatchesByProductIdQuery {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
    }
}
