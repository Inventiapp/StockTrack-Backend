package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

/**
 * Update product resource.
 */
public record UpdateProductResource(
        String name,
        String description,
        String categoryId,
        String providerId,
        Integer minStock,
        Double unitPrice,
        Boolean isActive
) {
    public UpdateProductResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (categoryId == null || categoryId.isBlank()) {
            throw new IllegalArgumentException("Category id is required");
        }
        if (providerId == null || providerId.isBlank()) {
            throw new IllegalArgumentException("Provider id is required");
        }
        if (minStock == null || minStock < 0) {
            throw new IllegalArgumentException("minStock must be non-negative");
        }
        if (unitPrice == null || unitPrice <= 0) {
            throw new IllegalArgumentException("unitPrice must be greater than 0");
        }
        if (isActive == null) {
            throw new IllegalArgumentException("isActive is required");
        }
    }
}