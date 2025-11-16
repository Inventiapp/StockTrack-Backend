package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

/**
 * Resource used to create a Product.
 * Validates the incoming payload at construction time.
 *
 * @param name       product name (required)
 * @param description product description (optional)
 * @param categoryId category id (required)
 * @param providerId provider id (required)
 * @param minStock   minimum stock quantity to trigger alert (required)
 * @param unitPrice  price per unit (required)
 * @param isActive   product active status (required)
 */
public record CreateProductResource(
        String name,
        String description,
        String categoryId,
        String providerId,
        Integer minStock,
        Double unitPrice,
        Boolean isActive
) {
    /**
     * Validate the resource.
     *
     * @throws IllegalArgumentException if any required field is null or blank.
     */
    public CreateProductResource {
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
