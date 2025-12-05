package com.inventiapp.stocktrack.iam.domain.model.commands;

import com.inventiapp.stocktrack.iam.domain.model.entities.Role;

import java.util.List;

/**
 * Command for registering a new user
 * @param email The email for the new user
 * @param password The password for the new user
 * @param roles The roles to assign to the user
 */
public record SignUpCommand(String email, String password, List<Role> roles) {
}

