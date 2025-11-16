package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProductCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProductCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteProductCommand;

import java.util.Optional;

/**
 * Command service for Product aggregate.
 * Handles creation, update and deletion of products.
 */
public interface ProductCommandService {

    /**
     * Handle product creation.
     * @param command create product command
     * @return generated product id
     */
    Long handle(CreateProductCommand command);

    /**
     * Handle product update.
     * @param command update product command
     * @return optional with updated Product if update succeeded
     */
    Optional<Product> handle(UpdateProductCommand command);

    /**
     * Handle product deletion.
     * @param command delete product command
     */
    void handle(DeleteProductCommand command);
}
