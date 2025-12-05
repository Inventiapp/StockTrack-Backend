package com.inventiapp.stocktrack.iam.domain.model.commands;

/**
 * Command for registering a new user (business owner).
 * Sign-up automatically creates ROLE_ADMIN with all permissions.
 * @param email The email for the new user
 * @param password The password for the new user
 */
public record SignUpCommand(String email, String password) {
}

