package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllProvidersQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetProviderByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * @name ProviderQueryService
 * @summary
 * This interface represents the service responsible for handling provider queries
 * (read operations) within the Inventory bounded context.
 * @since 1.0
 */
public interface ProviderQueryService {

    /**
     * Handles the get provider by id query.
     *
     * @param query the query containing the provider id
     * @return an Optional containing the Provider when found, or an empty Optional when not found.
     * @see GetProviderByIdQuery
     */
    Optional<Provider> handle(GetProviderByIdQuery query);

    /**
     * Handles the get all providers query.
     *
     * @param query the query object (may be extended later with paging/filtering)
     * @return list of all providers matching the query (may be empty)
     * @see GetAllProvidersQuery
     */
    List<Provider> handle(GetAllProvidersQuery query);
}
