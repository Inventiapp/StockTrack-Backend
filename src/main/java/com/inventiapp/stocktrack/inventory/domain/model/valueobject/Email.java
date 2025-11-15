package com.inventiapp.stocktrack.inventory.domain.model.valueobject;

import jakarta.persistence.Embeddable;

/**
 * Value object representing an email address.
 * @summary
 * This value object encapsulates the email of a provider. It validates that the email
 * is not null, not blank, and follows a basic email pattern.
 * @param address The email address. It cannot be null, empty, or invalid.
 * @see IllegalArgumentException
 * @since 1.0
 */
@Embeddable
public record Email(String address) {

    public Email {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!address.matches(regex)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
