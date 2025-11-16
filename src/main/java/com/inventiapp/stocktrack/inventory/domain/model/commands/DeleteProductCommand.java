package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * Command to delete an existing product.
 *
 * @param productId the id of the product to delete. Cannot be null.
 */
public record DeleteProductCommand(Long productId) {
    public DeleteProductCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId must be a positive number");
        }
    }
}
