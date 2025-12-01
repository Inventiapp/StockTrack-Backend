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
     * Resource for a kit item (product with price).
     * 
     * @param productId The product ID. Must be positive.
     * @param price The price of the product in this kit. Must be greater than 0.
     */
    public record KitItemResource(
            Long productId,
            Double price
    ) {
        public KitItemResource {
            if (productId == null || productId <= 0) {
                throw new IllegalArgumentException("Product ID must be a positive number");
            }
            if (price == null || price <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
        }
    }
}

