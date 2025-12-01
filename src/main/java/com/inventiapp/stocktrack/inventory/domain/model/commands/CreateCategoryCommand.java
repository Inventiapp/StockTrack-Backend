package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * CreateCategoryCommand
 * @summary
 * CreateCategoryCommand is a record class that represents the command to create a category.
 * @since 1.0
 */
public record CreateCategoryCommand(String name, String description) {
    /**
     * Validates the command.
     * @throws IllegalArgumentException If name is null or empty
     */
    public CreateCategoryCommand {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Category name cannot be null or empty");
    }
}


