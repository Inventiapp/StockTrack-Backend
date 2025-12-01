
package com.inventiapp.stocktrack.inventory.application.internal.queryservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesByProductIdQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetBatchByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.BatchQueryService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories.BatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of BatchQueryService.
 * Provides read operations for Batch aggregate.
 */
@Service
@Transactional(readOnly = true)
public class BatchQueryServiceImpl implements BatchQueryService {

    private final BatchRepository batchRepository;

    public BatchQueryServiceImpl(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    public Optional<Batch> handle(GetBatchByIdQuery query) {
        return batchRepository.findById(query.batchId());
    }

    @Override
    public List<Batch> handle(GetAllBatchesQuery query) {
        return batchRepository.findAll();
    }

    @Override
    public List<Batch> handle(GetAllBatchesByProductIdQuery query) {
        return batchRepository.findByProductIdOrderByExpirationDateAsc(query.productId());
    }
}
