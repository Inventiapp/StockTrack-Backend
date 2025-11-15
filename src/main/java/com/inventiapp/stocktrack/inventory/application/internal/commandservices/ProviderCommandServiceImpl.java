package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.exceptions.ProviderNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.exceptions.ProviderRequestException;
import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Email;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.PhoneNumber;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Ruc;
import com.inventiapp.stocktrack.inventory.domain.services.ProviderCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.ProviderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of ProviderCommandService.
 * @summary
 * Performs domain operations for Provider aggregate: create, update and delete.
 * Exceptions from persistence layer are translated into domain-friendly exceptions.
 * @since 1.0
 */
@Service
public class ProviderCommandServiceImpl implements ProviderCommandService {

    private final ProviderRepository providerRepository;

    public ProviderCommandServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * Handles the creation of a provider.
     * @param command CreateProviderCommand with provider data
     * @return generated provider id
     */
    @Override
    public Long handle(CreateProviderCommand command) {
        Provider provider = new Provider(command);
        try {
            Provider saved = providerRepository.save(provider);
            return saved.getId();
        } catch (DataIntegrityViolationException ex) {
            // Translate persistence exceptions to a domain exception with a friendly message
            throw new ProviderRequestException(ex.getMostSpecificCause() != null
                    ? ex.getMostSpecificCause().getMessage()
                    : ex.getMessage());
        } catch (RuntimeException ex) {
            // Catch any domain validation or unexpected runtime exception and wrap it
            throw new ProviderRequestException(ex.getMessage());
        }
    }

    /**
     * Handles updating a provider.
     * @param command UpdateProviderCommand containing provider id and updated values
     * @return Optional with updated provider if exists
     */
    @Override
    public Optional<Provider> handle(UpdateProviderCommand command) {
        Long providerId = command.providerId();
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException(providerId));

        try {
            provider.updateInformation(command);
            Provider saved = providerRepository.save(provider);
            return Optional.of(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ProviderRequestException(ex.getMostSpecificCause() != null
                    ? ex.getMostSpecificCause().getMessage()
                    : ex.getMessage());
        } catch (RuntimeException ex) {
            throw new ProviderRequestException(ex.getMessage());
        }
    }

    /**
     * Handles deletion of a provider.
     * @param command DeleteProviderCommand containing provider id
     */
    @Override
    public void handle(DeleteProviderCommand command) {
        Long providerId = command.providerId();
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException(providerId));

        try {
            providerRepository.delete(provider);
        } catch (DataIntegrityViolationException ex) {
            throw new ProviderRequestException(ex.getMostSpecificCause() != null
                    ? ex.getMostSpecificCause().getMessage()
                    : ex.getMessage());
        } catch (RuntimeException ex) {
            throw new ProviderRequestException(ex.getMessage());
        }
    }
}
