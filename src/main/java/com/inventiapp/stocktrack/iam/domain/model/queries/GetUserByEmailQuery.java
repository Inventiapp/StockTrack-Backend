package com.inventiapp.stocktrack.iam.domain.model.queries;

/**
 * Query to get a user by email
 * @param email The email
 */
public record GetUserByEmailQuery(String email) {
}

