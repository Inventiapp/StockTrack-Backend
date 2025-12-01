package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

/**
 * Resource record for a kit.
 * 
 * @summary
 * This record represents the resource for a kit.
 * It contains the ID, name, items (products with prices), createdAt, and updatedAt.
 * @since 1.0
 */
public record KitResource(
        Long id,
        String name,
        List<KitItemResource> items,
        Date createdAt,
        Date updatedAt
) {
    /**
     * Resource for a kit item (product with quantity and price).
     * 
     * @param productId The product ID
     * @param quantity The quantity of products
     * @param price The total price for the quantity of products in this kit
     */
    public record KitItemResource(
            Long productId,
            Integer quantity,
            Double price
    ) {}
}

