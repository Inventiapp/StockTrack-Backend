package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * CreateProviderResource
 * @summary
 * Resource used to create a Provider via REST API. Carries raw input strings which
 * will be validated and mapped to domain value objects by the application layer.
 * @since 1.0
 */
public record CreateProviderResource(
        @NotBlank(message = "firstName is required")
        String firstName,

        @NotBlank(message = "lastName is required")
        String lastName,

        @NotBlank(message = "phoneNumber is required")
        String phoneNumber,

        @NotBlank(message = "email is required")
        String email,

        String ruc
) {
    /**
     * Compact constructor used to perform simple validation.
     * - trims whitespace
     * - checks phone format if provided
     *
     * This performs lightweight validation; domain-level validation is done
     * when mapping to Value Objects (Email, PhoneNumber, Ruc).
     */
    public CreateProviderResource {
        if (firstName != null) firstName = firstName.trim();
        if (lastName != null) lastName = lastName.trim();
        if (email != null) email = email.trim();
        if (ruc != null) ruc = ruc.trim();

        if (phoneNumber != null && !phoneNumber.isBlank()) {
            String digits = phoneNumber.trim().replaceAll("[\\s\\-()]", "");
            if (!digits.matches("\\d{9}")) {
                throw new IllegalArgumentException("phoneNumber must be exactly 9 digits, without prefix");
            }
            phoneNumber = digits;
        } else {
            phoneNumber = null;
        }
    }
}
