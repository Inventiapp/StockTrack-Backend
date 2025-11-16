package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * Command to update an existing product.
 *
 * @param productId   the product id to update. Cannot be null.
 * @param name        the product name. Cannot be null or blank.
 * @param description the product description. Can be null or blank.
 * @param categoryId  the category id. Cannot be null or blank.
 * @param providerId  the provider id. Cannot be null or blank.
 * @param minStock    the minimum stock threshold (alert level). Cannot be null or negative.
 * @param unitPrice   the unit price. Cannot be null and must be greater than 0.
 * @param isActive    whether the product is active. Cannot be null.
 */
public record UpdateProductCommand(
        Long productId,
        String name,
        String description,
        String categoryId,
        String providerId,
        Integer minStock,
        Double unitPrice,
        Boolean isActive
) {
    public UpdateProductCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId must be a positive number");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (categoryId == null || categoryId.isBlank()) {
            throw new IllegalArgumentException("categoryId cannot be null or blank");
        }
        if (providerId == null || providerId.isBlank()) {
            throw new IllegalArgumentException("providerId cannot be null or blank");
        }
        if (minStock == null || minStock < 0) {
            throw new IllegalArgumentException("minStock cannot be null or negative");
        }
        if (unitPrice == null || unitPrice <= 0) {
            throw new IllegalArgumentException("unitPrice must be greater than 0");
        }
        if (isActive == null) {
            throw new IllegalArgumentException("isActive cannot be null");
        }
    }
}
