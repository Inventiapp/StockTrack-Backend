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
 * ProviderQueryService Implementation
 *
 * @summary
 * Implementation of the ProviderQueryService interface.
 * It is responsible for handling provider queries.
 *
 * @since 1.0
 */
@Service
public class ProviderQueryServiceImpl implements ProviderQueryService {

    private final ProviderRepository providerRepository;

    public ProviderQueryServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Provider> handle(GetAllProvidersQuery query) {
        return providerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Provider> handle(GetProviderByIdQuery query) {
        return providerRepository.findById(query.id());
    }
}
