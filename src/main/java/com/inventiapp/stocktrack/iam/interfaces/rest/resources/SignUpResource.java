package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for sign-up request
 * @param email The email
 * @param password The password
 * @param roles List of role names
 */
public record SignUpResource(String email, String password, List<String> roles) {
}

