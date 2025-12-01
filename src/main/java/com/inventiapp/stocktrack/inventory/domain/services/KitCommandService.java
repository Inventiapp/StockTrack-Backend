package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Kit;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateKitCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteKitCommand;

import java.util.Optional;

/**
 * @name KitCommandService
 * 
 * @summary
 * This interface represents the service to handle kit commands.
 * @since 1.0
 */
public interface KitCommandService {
    /**
     * Handles the create kit command.
     * @param command The create kit command.
     * @return The created kit.
     * 
     * @throws IllegalArgumentException If kit name already exists
     * @see CreateKitCommand
     */
    Optional<Kit> handle(CreateKitCommand command);

    /**
     * Handles the delete kit command.
     * @param command The delete kit command.
     * 
     * @throws KitNotFoundException If kit is not found
     * @see DeleteKitCommand
     */
    void handle(DeleteKitCommand command);
}

