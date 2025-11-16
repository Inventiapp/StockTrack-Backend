package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.BatchResource;

/**
 * Assembler to convert a Batch aggregate to a BatchResource.
 */
public class BatchResourceFromEntityAssembler {
    /**
     * Converts a Batch entity into a BatchResource.
     *
     * @param batch the Batch aggregate
     * @return BatchResource for API responses
     */
    public static BatchResource toResource(Batch batch) {
        if (batch == null) {
            throw new IllegalArgumentException("Batch cannot be null");
        }

        return new BatchResource(
                batch.getId(),
                batch.getProductId(),
                batch.getQuantity(),
                batch.getExpirationDate(),
                batch.getReceptionDate()
        );
    }
}
