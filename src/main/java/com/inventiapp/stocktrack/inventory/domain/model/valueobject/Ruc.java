package com.inventiapp.stocktrack.inventory.domain.model.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Ruc value object
 *
 * @summary
 * The Ruc class represents the unique tax identification number (RUC)
 * assigned to a Provider in the Inventory bounded context.
 * It ensures that every RUC value follows a valid format and encapsulates
 * validation logic at the domain level.
 * @since 1.0
 */
@Embeddable
@Getter
@NoArgsConstructor
public class Ruc {

    private String value;

    /**
     * Constructs a new Ruc instance with the specified value.
     *
     * @param value The RUC value to be assigned.
     * @throws IllegalArgumentException if the RUC value is invalid.
     */
    public Ruc(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("RUC cannot be null or blank");

        if (!value.matches("\\d{11}"))
            throw new IllegalArgumentException("RUC must contain exactly 11 digits");

        this.value = value;
    }

    /**
     * Checks if the RUC value equals another RUC.
     *
     * @param other The Ruc instance to compare with.
     * @return true if both RUC values are equal, false otherwise.
     */
    public boolean equals(Ruc other) {
        if (other == null) return false;
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
