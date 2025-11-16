package com.inventiapp.stocktrack.inventory.domain.model.queries;

/**
 * Query to get a batch by id.
 *
 * @param batchId Batch id.
 */
public record GetBatchByIdQuery(Long batchId) {
    /**
     * Constructor validation.
     *
     * @param batchId Batch id. Must be greater than 0 and not null.
     * @throws IllegalArgumentException if batchId is null or not greater than 0.
     */
    public GetBatchByIdQuery {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("batchId is required and must be greater than 0");
        }
    }
}

