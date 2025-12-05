package com.inventiapp.stocktrack.inventory.domain.model.valueobject;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a Peruvian RUC (Registro Único de Contribuyentes).
 * @summary
 * This value object encapsulates the RUC number of a provider. It validates that the RUC
 * is exactly 11 numeric digits. This ensures correctness at the domain level.
 * @param value The RUC number. It must be exactly 11 digits.
 * @see IllegalArgumentException
 * @since 1.0
 */
@Embeddable
public record Ruc(String value) {

    public Ruc {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("RUC cannot be null or empty");
        }

        if (value.length() != 11) {
            throw new IllegalArgumentException("RUC must have exactly 11 digits");
        }

        if (!value.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("RUC must contain only numeric characters");
        }
    }
}
