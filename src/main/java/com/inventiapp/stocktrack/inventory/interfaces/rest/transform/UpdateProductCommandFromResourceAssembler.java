package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProductCommand;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.UpdateProductResource;

/**
 * Assembler to convert a UpdateProductResource to a UpdateProductCommand.
 */
public class UpdateProductCommandFromResourceAssembler {

    /**
     * Converts a UpdateProductResource to a UpdateProductCommand.
     *
     * @param productId The product ID.
     * @param resource  The {@link UpdateProductResource} to convert.
     * @return The resulting {@link UpdateProductCommand}.
     */
    public static UpdateProductCommand toCommandFromResource(Long productId, UpdateProductResource resource) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId must be a positive number");
        }
        if (resource == null) {
            throw new IllegalArgumentException("UpdateProductResource cannot be null");
        }

        return new UpdateProductCommand(
                productId,
                resource.name(),
                resource.description(),
                resource.categoryId(),
                resource.providerId(),
                resource.minStock(),
                resource.unitPrice(),
                resource.isActive()
        );
    }
}