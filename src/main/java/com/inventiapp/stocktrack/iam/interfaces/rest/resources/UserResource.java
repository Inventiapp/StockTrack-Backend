package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for user response
 * @param id The user ID
 * @param email The email
 * @param roles List of role names
 */
public record UserResource(Long id, String email, List<String> roles) {
}

