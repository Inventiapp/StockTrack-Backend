package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Category;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateCategoryCommand;

import java.util.Optional;

/**
 * @name CategoryCommandService
 * @summary
 * This interface represents the service to handle category commands.
 * @since 1.0
 */
public interface CategoryCommandService {
    /**
     * Handles the create category command.
     * @param command The create category command.
     * @return The created category.
     *
     * @throws IllegalArgumentException If category name already exists
     * @see CreateCategoryCommand
     */
    Optional<Category> handle(CreateCategoryCommand command);
}

