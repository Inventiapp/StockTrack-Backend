package com.inventiapp.stocktrack.inventory.application.internal.queryservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetBatchByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.BatchQueryService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of BatchQueryService.
 * @summary
 * Provides read operations for Batch aggregate: find by id and list all.
 * Read methods are marked as read-only transactions.
 * @since 1.0
 */
@Service
public class BatchQueryServiceImpl implements BatchQueryService {

    private final BatchRepository batchRepository;

    public BatchQueryServiceImpl(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    /**
     * Handle query to get a batch by id.
     * @param query GetBatchByIdQuery
     * @return optional with batch if found
     */
    @Override
    public Optional<Batch> handle(GetBatchByIdQuery query) {
        return batchRepository.findById(query.batchId());
    }

    /**
     * Handle query to get all batches.
     * @param query GetAllBatchesQuery
     * @return list of batches
     */
    @Override
    public List<Batch> handle(GetAllBatchesQuery query) {
        return batchRepository.findAll();
    }
}

