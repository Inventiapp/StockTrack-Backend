package com.inventiapp.stocktrack.inventory.domain.exceptions;

public class BatchNotFoundException extends RuntimeException {

    public BatchNotFoundException(Long batchId) {
        super("Batch with ID %s was not found".formatted(batchId));
    }
}
