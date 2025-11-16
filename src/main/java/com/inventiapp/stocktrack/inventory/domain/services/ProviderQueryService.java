package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllProvidersQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetProviderByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Provider query service contract.
 * @summary
 * This interface defines query operations for Provider aggregate (fetch all, fetch by id).
 * Implementations should retrieve read models or aggregate projections as required.
 * @since 1.0
 */
public interface ProviderQueryService {
    /**
     * Handle query to get all providers.
     * @param query get all providers query
     * @return list of providers
     */
    List<Provider> handle(GetAllProvidersQuery query);

    /**
     * Handle query to get a provider by id.
     * @param query get provider by id query
     * @return optional with found provider
     */
    Optional<Provider> handle(GetProviderByIdQuery query);
}
