package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateBatchCommand;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.UpdateBatchResource;

public class UpdateBatchCommandFromEntityResource {
    public static UpdateBatchCommand toCommandFromResource(Long batchId, UpdateBatchResource resource) {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("batchId must be a positive number");
        }
        if (resource.quantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        return new UpdateBatchCommand(
                batchId,
                resource.quantity()
        );
    }
}
