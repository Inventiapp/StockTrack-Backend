package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.exceptions.BatchNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.exceptions.ProductNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateBatchCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteBatchCommand;
import com.inventiapp.stocktrack.inventory.domain.model.events.BatchCreatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.events.BatchDeletedEvent;
import com.inventiapp.stocktrack.inventory.domain.services.BatchCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories.BatchRepository;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Implementation of BatchCommandService.
 * @summary
 * Performs domain operations for Batch aggregate: create and delete.
 * Exceptions from persistence layer are translated into domain-friendly exceptions.
 * @since 1.0
 */
@Service
public class BatchCommandServiceImpl implements BatchCommandService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;

    public BatchCommandServiceImpl(BatchRepository batchRepository, ProductRepository productRepository) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
    }

    /**
     * Handles the creation of a batch.
     * Validates that the product exists.
     * Registers a BatchCreatedEvent on the aggregate.
     *
     * @param command CreateBatchCommand with batch data
     * @return generated batch id
     * @throws ProductNotFoundException if the product does not exist
     */
    @Override
    public Long handle(CreateBatchCommand command) {
        if (!productRepository.existsById(command.productId())) {
            throw new ProductNotFoundException(command.productId());
        }

        Batch batch = new Batch(command);

        batch.addDomainEvent(new BatchCreatedEvent(
                batch,
                batch.getId(),
                batch.getProductId(),
                batch.getQuantity(),
                batch.getExpirationDate(),
                batch.getReceptionDate()
        ));

        try {
            Batch saved = batchRepository.save(batch);
            return saved.getId();
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error saving batch: " +
                    (ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage()));
        }
    }

    /**
     * Handles deletion of a batch.
     * Registers a BatchDeletedEvent on the aggregate before deleting from repository.
     *
     * @param command DeleteBatchCommand containing batch id
     * @throws BatchNotFoundException if the batch does not exist
     */
    @Override
    public void handle(DeleteBatchCommand command) {
        Batch batch = batchRepository.findById(command.batchId())
                .orElseThrow(() -> new BatchNotFoundException(command.batchId()));

        batch.addDomainEvent(new BatchDeletedEvent(batch, batch.getId()));

        try {
            batchRepository.delete(batch);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error deleting batch: " +
                    (ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage()));
        }
    }
}