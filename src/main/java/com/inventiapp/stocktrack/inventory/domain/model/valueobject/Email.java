package com.inventiapp.stocktrack.inventory.domain.model.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

/**
 * Email value object
 *
 * @summary
 * The Email value object represents an email address used by domain entities
 * (e.g. Provider). It validates format on construction and is immutable.
 * @since 1.0
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private String value;

    /**
     * Constructs a new Email value object.
     *
     * @param value the email address string (non-null, non-empty, valid format)
     * @throws IllegalArgumentException when value is null/empty or format is invalid
     */
    public Email(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String normalized = value.trim();
        if (!isValidEmail(normalized)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.value = normalized.toLowerCase();
    }

    private boolean isValidEmail(String email) {
        // Simple but practical regex — covers most common cases.
        // You may replace with stricter validation if needed.
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email other = (Email) o;
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
