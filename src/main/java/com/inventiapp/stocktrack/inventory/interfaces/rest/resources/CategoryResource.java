package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import java.util.Date;

/**
 * Resource record for a category.
 * @summary
 * This record represents the resource for a category.
 * It contains the ID, name, description, createdAt, and updatedAt.
 * @since 1.0
 */
public record CategoryResource(
        Long id,
        String name,
        String description,
        Date createdAt,
        Date updatedAt
) {}

