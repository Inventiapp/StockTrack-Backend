package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesByProductIdQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetBatchByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service for Batch aggregate.
 * Provides methods to fetch batches by id or all batches.
 */
public interface BatchQueryService {

    /**
     * Handle query to get a batch by id.
     * @param query get batch by id query
     * @return optional with found batch
     */
    Optional<Batch> handle(GetBatchByIdQuery query);

    /**
     * Handle query to get all batches.
     * @param query get all batches query
     * @return list of batches
     */
    List<Batch> handle(GetAllBatchesQuery query);

    List<Batch> handle(GetAllBatchesByProductIdQuery getAllBatchesByProductIdQuery);
}

