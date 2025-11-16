package com.inventiapp.stocktrack.inventory.domain.exceptions;

/**
 * Exception thrown when an error occurs while processing a provider request.
 * @summary
 * This exception is thrown when an error occurs while creating or updating a provider.
 * @see RuntimeException
 */
public class ProviderRequestException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param exceptionMessage The message describing the underlying issue.
     */
    public ProviderRequestException(String exceptionMessage) {
        super("Error while processing provider request: %s".formatted(exceptionMessage));
    }
}