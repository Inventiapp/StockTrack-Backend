package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * Command to delete a provider
 *
 * @param providerId the provider id.
 *                   Cannot be null or less than 1
 */
public record DeleteProviderCommand(Long providerId) {

    /**
     * Constructor
     *
     * @throws IllegalArgumentException if providerId is null or less than 1
     */
    public DeleteProviderCommand {
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("providerId cannot be null or less than 1");
        }
    }
}
