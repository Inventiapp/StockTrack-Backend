package com.inventiapp.stocktrack.inventory.domain.model.commands;

import java.util.List;

/**
 * Command to create a kit.
 * 
 * @param name The kit name. Cannot be null or blank.
 * @param items The list of products with their prices. Cannot be null or empty.
 * @since 1.0
 */
public record CreateKitCommand(
        String name,
        List<KitItemCommand> items
) {
    /**
     * Constructor validation.
     * @throws IllegalArgumentException if any required field is missing or invalid.
     */
    public CreateKitCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Kit name cannot be null or blank");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Kit must have at least one product");
        }
    }

    /**
     * Represents a product item within a kit.
     * @param productId The product ID. Cannot be null or less than or equal to 0.
     * @param quantity The quantity of products. Cannot be null or less than or equal to 0.
     * @param price The total price for the quantity of products in this kit. Cannot be null or less than or equal to 0.
     */
    public record KitItemCommand(
            Long productId,
            Integer quantity,
            Double price
    ) {
        public KitItemCommand {
            if (productId == null || productId <= 0) {
                throw new IllegalArgumentException("Product ID must be a positive number");
            }
            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            if (price == null || price <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
        }
    }
}

