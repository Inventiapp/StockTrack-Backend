package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * Command to create a product.
 *
 * @param name       the product name. Cannot be null or blank.
 * @param description the product description. Can be null or blank.
 * @param categoryId the category id. Cannot be null or blank.
 * @param providerId the provider id. Cannot be null or blank.
 * @param minStock   the minimum stock threshold (alert level). Cannot be null or negative.
 * @param unitPrice  the unit price. Cannot be null and must be greater than 0.
 * @param isActive   whether the product is active (available). Cannot be null.
 * @since 1.0
 */
public record CreateProductCommand(
        String name,
        String description,
        String categoryId,
        String providerId,
        Integer minStock,
        Double unitPrice,
        Boolean isActive
) {
    /**
     * Constructor validation.
     *
     * @throws IllegalArgumentException if any required field is missing or invalid.
     */
    public CreateProductCommand {
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
