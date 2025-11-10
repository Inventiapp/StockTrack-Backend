package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;

import java.util.Optional;

/**
 * @name ProviderCommandService
 * @summary
 * This interface represents the service responsible for handling provider commands
 * (write operations) within the Inventory bounded context.
 * @since 1.0
 */
public interface ProviderCommandService {

    /**
     * Handles the create provider command.
     *
     * @param command the create provider command
     * @return an Optional containing the created Provider if creation succeeded,
     *         or an empty Optional if the operation could not be completed.
     *
     * @throws IllegalArgumentException when validation fails (e.g. duplicate email)
     * @see CreateProviderCommand
     */
    Optional<Provider> handle(CreateProviderCommand command);
}
