package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProviderCommand;

import java.util.Optional;

/**
 * Provider command service contract.
 * @summary
 * This interface defines command operations for Provider aggregate (create, update, delete).
 * Implementations should execute domain logic and return identifiers or optional aggregates when appropriate.
 * @since 1.0
 */
public interface ProviderCommandService {
    /**
     * Handle provider creation.
     * @param command create provider command
     * @return generated provider id
     */
    Long handle(CreateProviderCommand command);

    /**
     * Handle provider update.
     * @param command update provider command
     * @return optional with updated Provider if update succeeded
     */
    Optional<Provider> handle(UpdateProviderCommand command);

    /**
     * Handle provider deletion.
     * @param command delete provider command
     */
    void handle(DeleteProviderCommand command);
}
