package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

/**
 * Resource for sign-up request.
 * @param email The email
 * @param password The password
 */
public record SignUpResource(String email, String password) {
}

