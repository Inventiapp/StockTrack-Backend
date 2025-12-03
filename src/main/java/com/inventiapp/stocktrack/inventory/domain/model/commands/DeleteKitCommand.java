package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * Command to delete an existing kit.
 * 
 * @param kitId The id of the kit to delete. Cannot be null.
 * @since 1.0
 */
public record DeleteKitCommand(Long kitId) {
    public DeleteKitCommand {
        if (kitId == null || kitId <= 0) {
            throw new IllegalArgumentException("kitId must be a positive number");
        }
    }
}

