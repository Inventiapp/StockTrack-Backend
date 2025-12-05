package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for user response
 * @param id The user ID
 * @param email The email
 * @param roles List of role names
 * @param permissions List of permission names for module access
 */
public record UserResource(Long id, String email, List<String> roles, List<String> permissions) {
}

