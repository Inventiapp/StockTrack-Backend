package com.inventiapp.stocktrack.inventory.domain.model.queries;

/**
 * Query to get provider by id.
 * @param providerId Provider id.
 */
public record GetProviderByIdQuery(Long providerId) {
    /**
     * Constructor.
     * @param providerId Provider id.
     *                   Must be greater than 0.
     *                   Must not be null.
     * @throws IllegalArgumentException If the provider ID is invalid.
     */
    public GetProviderByIdQuery {
        if (providerId == null || providerId <= 0)
            throw new IllegalArgumentException("providerId is required and must be greater than 0.");
    }
}
