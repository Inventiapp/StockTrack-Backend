package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

/**
 * Resource for authenticated user response
 * @param id The user ID
 * @param email The email
 * @param token The JWT token
 */
public record AuthenticatedUserResource(Long id, String email, String token) {
}

