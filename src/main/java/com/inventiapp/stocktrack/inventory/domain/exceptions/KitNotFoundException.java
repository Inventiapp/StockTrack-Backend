package com.inventiapp.stocktrack.inventory.domain.exceptions;

/**
 * Exception thrown when a kit is not found.
 * 
 * @summary
 * This exception is thrown when a kit is not found in the database.
 * @see RuntimeException
 * @since 1.0
 */
public class KitNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param kitId The ID of the kit that was not found.
     */
    public KitNotFoundException(Long kitId) {
        super(String.format("Kit with id '%s' not found", kitId));
    }
}

