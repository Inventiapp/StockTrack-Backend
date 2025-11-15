package com.inventiapp.stocktrack.inventory.domain.exceptions;

/**
 * Exception thrown when a provider is not found.
 * @summary
 * This exception is thrown when a provider is not found in the database.
 * @see RuntimeException
 */
public class ProviderNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param providerId The ID of the provider that was not found.
     */
    public ProviderNotFoundException(Long providerId) {
        super(String.format("Provider with ID %s not found.", providerId));
    }
}