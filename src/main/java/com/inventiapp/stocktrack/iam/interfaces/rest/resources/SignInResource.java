package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

/**
 * Resource for sign-in request
 * @param email The email
 * @param password The password
 */
public record SignInResource(String email, String password) {
}

