package com.inventiapp.stocktrack.iam.domain.model.commands;

/**
 * Command for user sign-in
 * @param email The email
 * @param password The password
 */
public record SignInCommand(String email, String password) {
}

