package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for updating a user from staff management.
 * @param email The new email (optional)
 * @param password The new password (optional, only if changing password)
 * @param permissions List of permission names for module access
 */
public record UpdateUserResource(String email, String password, List<String> permissions) {
}

