package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Resource record for creating a category.
 * @summary
 * This record represents the resource for creating a category.
 * It contains the name and description.
 * @since 1.0
 */
public record CreateCategoryResource(
        @NotBlank(message = "Category name is required")
        String name,
        String description
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException If name is null or empty
     */
    public CreateCategoryResource {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Category name cannot be null or empty");
    }
}

