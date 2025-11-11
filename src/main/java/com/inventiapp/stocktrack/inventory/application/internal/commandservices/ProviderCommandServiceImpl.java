package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.services.ProviderCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ProviderCommandService Implementation
 *
 * @summary
 * Implementation of the ProviderCommandService interface.
 * It is responsible for handling provider creation commands.
 *
 * @since 1.0
 */
@Service
public class ProviderCommandServiceImpl implements ProviderCommandService {

    private final ProviderRepository providerRepository;

    public ProviderCommandServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<Provider> handle(CreateProviderCommand command) {
        // Validate presence of required fields (basic)
        command.validate();

        // Validate that provider email doesn't already exist
        if (providerRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Provider with email '" + command.email() + "' already exists");
        }

        // Validate that provider RUC doesn't already exist
        if (providerRepository.existsByRuc(command.ruc())) {
            throw new IllegalArgumentException("Provider with RUC '" + command.ruc() + "' already exists");
        }

        Provider provider = Provider.from(command);

        Provider savedProvider = providerRepository.save(provider);
        return Optional.of(savedProvider);
    }
}
