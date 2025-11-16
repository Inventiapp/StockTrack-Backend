package com.inventiapp.stocktrack.inventory.domain.model.valueobject;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a phone number.
 * @summary
 * This value object encapsulates the provider's phone number. It only checks that the
 * number is not null, not blank, and contains only digits. This keeps validation simple and domain-safe.
 * @param number The phone number. It cannot be null, empty, or contain non-digit characters.
 * @see IllegalArgumentException
 * @since 1.0
 */
@Embeddable
public record PhoneNumber(String number) {

    public PhoneNumber {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        if (!number.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Phone number must contain only numeric characters");
        }
    }
}
