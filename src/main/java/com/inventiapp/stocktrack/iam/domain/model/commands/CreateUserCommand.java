package com.inventiapp.stocktrack.iam.domain.model.commands;

import java.util.List;

/**
 * Command for creating a user from staff management.
 * Users created by admin always get ROLE_USER.
 * Permissions control access to specific modules.
 * @param email The email for the new user
 * @param password The password for the new user
 * @param permissions List of permission names for module access
 */
public record CreateUserCommand(String email, String password, List<String> permissions) {
}

