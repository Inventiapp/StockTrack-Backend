package com.inventiapp.stocktrack.iam.domain.model.commands;

import java.util.List;

/**
 * Command for updating a user from staff management.
 * @param userId The user ID to update
 * @param email The new email (optional, can be null to keep current)
 * @param password The new password (optional, can be null to keep current)
 * @param permissions List of permission names for module access
 */
public record UpdateUserCommand(Long userId, String email, String password, List<String> permissions) {
}

