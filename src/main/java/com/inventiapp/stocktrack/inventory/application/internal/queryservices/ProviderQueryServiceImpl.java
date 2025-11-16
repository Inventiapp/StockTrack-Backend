package com.inventiapp.stocktrack.inventory.application.internal.queryservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllProvidersQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetProviderByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.ProviderQueryService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ProviderQueryService.
 * @summary
 * Provides read operations for Provider aggregate: list all and find by id.
 * Read methods are marked as read-only transactions.
 * @since 1.0
 */
@Service
public class ProviderQueryServiceImpl implements ProviderQueryService {

    private final ProviderRepository providerRepository;

    public ProviderQueryServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * Handle query to get all providers.
     * @param query GetAllProvidersQuery
     * @return list of providers
     */
    @Override
    public List<Provider> handle(GetAllProvidersQuery query) {
        return providerRepository.findAll();
    }

    /**
     * Handle query to get a provider by id.
     * @param query GetProviderByIdQuery
     * @return optional with provider if found
     */
    @Override
    public Optional<Provider> handle(GetProviderByIdQuery query) {
        return providerRepository.findById(query.providerId());
    }
}
