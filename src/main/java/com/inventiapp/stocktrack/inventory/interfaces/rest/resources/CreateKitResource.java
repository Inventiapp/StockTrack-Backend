package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Resource record for creating a kit.
 * 
 * @summary
 * This record represents the resource for creating a kit.
 * It contains the name and list of products with their prices.
 * @since 1.0
 */
public record CreateKitResource(
        @NotBlank(message = "Kit name is required")
        String name,
        @NotEmpty(message = "Kit must have at least one product")
        @Valid
        List<KitItemResource> items
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException If name is null or empty
     */
    public CreateKitResource {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Kit name cannot be null or empty");
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Kit must have at least one product");
    }

    /**
     * Resource for a kit item (product with quantity and price).
     * 
     * @param productId The product ID. Must be positive.
     * @param quantity The quantity of products. Must be greater than 0.
     * @param price The total price for the quantity of products in this kit. Must be greater than 0.
     */
    public record KitItemResource(
            Long productId,
            Integer quantity,
            Double price
    ) {
        public KitItemResource {
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

