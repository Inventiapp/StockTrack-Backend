package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.exceptions.ProviderNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.exceptions.ProviderRequestException;
import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProviderCreatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProviderUpdatedEvent;
import com.inventiapp.stocktrack.inventory.domain.services.ProviderCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories.ProviderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
     * Registers a ProviderCreatedEvent on the persisted aggregate so the event
     * includes the DB-assigned id.
     *
     * @param command CreateProviderCommand with provider data
     * @return generated provider id
     */
    @Override
    public Long handle(CreateProviderCommand command) {
        Provider provider = new Provider(command);
        try {
            Provider saved = providerRepository.save(provider);

            // Register a created event using the saved entity so the id is available.
            saved.addDomainEvent(new ProviderCreatedEvent(
                    saved,
                    saved.getId(),
                    saved.getFirstName(),
                    saved.getLastName(),
                    saved.getPhoneNumber() != null ? saved.getPhoneNumber().number() : null,
                    saved.getEmail() != null ? saved.getEmail().address() : null,
                    saved.getRuc() != null ? saved.getRuc().value() : null
            ));

            return saved.getId();
        } catch (DataIntegrityViolationException ex) {
            throw new ProviderRequestException(ex.getMostSpecificCause() != null
                    ? ex.getMostSpecificCause().getMessage()
                    : ex.getMessage());
        } catch (RuntimeException ex) {
            throw new ProviderRequestException(ex.getMessage());
        }
    }

    /**
     * Handles updating a provider.
     * The aggregate's updateInformation(...) registers ProviderUpdatedEvent internally.
     *
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

            saved.addDomainEvent(new ProviderUpdatedEvent(
                    saved,
                    saved.getId(),
                    saved.getFirstName(),
                    saved.getLastName(),
                    saved.getPhoneNumber() != null ? saved.getPhoneNumber().number() : null,
                    saved.getEmail() != null ? saved.getEmail().address() : null,
                    saved.getRuc() != null ? saved.getRuc().value() : null
            ));

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
     * Marks the aggregate as deleted (registers ProviderDeletedEvent) before deleting from repository.
     *
     * @param command DeleteProviderCommand containing provider id
     */
    @Override
    public void handle(DeleteProviderCommand command) {
        Long providerId = command.providerId();
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException(providerId));

        try {
            provider.markAsDeleted(null);

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
