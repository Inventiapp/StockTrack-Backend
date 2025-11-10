package com.inventiapp.stocktrack.inventory.domain.model.queries;

/**
 * GetProviderByIdQuery
 * @summary
 * Query object that represents the intention to fetch a Provider aggregate by its identifier.
 * This class is part of the domain query model and can be used by application query services.
 * @since 1.0
 */
public record GetProviderByIdQuery(Long id) {

    /**
     * Basic validation helper.
     *
     * @throws IllegalArgumentException if id is null or not positive.
     */
    public void validate() {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be a positive non-null value");
        }
    }
}
