package com.inventiapp.stocktrack.inventory.domain.model.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

/**
 * PhoneNumber value object
 *
 * @summary
 * The PhoneNumber value object represents a Peruvian local phone number used by domain entities.
 * It enforces the rule: exactly 9 digits, numeric only, no country code or prefixes.
 * @since 1.0
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {

    @Column(name = "phone_number", length = 9)
    private String value;

    /**
     * Constructs a new PhoneNumber.
     *
     * @param value phone number string (must be 9 digits)
     * @throws IllegalArgumentException when value is null/empty or does not match the required format
     */
    public PhoneNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        String digits = value.trim();
        digits = digits.replaceAll("[\\s\\-()]", "");
        if (!digits.matches("\\d{9}")) {
            throw new IllegalArgumentException("Phone number must be exactly 9 digits, without prefix");
        }
        this.value = digits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber other = (PhoneNumber) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
