package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import java.util.Date;

/**
 * Resource record for a batch.
 * @summary
 * This record represents the resource for a batch.
 * It contains the ID, productId, quantity, expirationDate, and receptionDate.
 * @since 1.0
 */
public record BatchResource(
        Long id,
        Long productId,
        Integer quantity,
        Date expirationDate,
        Date receptionDate
) {}
