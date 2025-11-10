package com.inventiapp.stocktrack.inventory.domain.model.commands;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;

/**
 * CreateProviderCommand
 * @summary
 * Command that represents the intention to create a Provider in the Inventory bounded context.
 * Carries raw input strings from the REST layer; provides mapping helper to domain Provider.
 */
public record CreateProviderCommand(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String ruc
) {

    /**
     * Basic presence validation. Throws IllegalArgumentException on missing required fields.
     * Format-specific validation (email/ruc/phone) is delegated to the corresponding Value Objects.
     */
    public void validate() {
        if (isBlank(firstName)) throw new IllegalArgumentException("firstName is required");
        if (isBlank(lastName)) throw new IllegalArgumentException("lastName is required");
        if (isBlank(phoneNumber)) throw new IllegalArgumentException("phoneNumber is required");
        if (isBlank(email)) throw new IllegalArgumentException("email is required");
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Convenience mapping helper that constructs the Provider aggregate from this command.
     * This delegates the creation of value objects (Email, PhoneNumber, Ruc) to the domain layer.
     *
     * @return Provider built from command data (may throw exceptions from VOs if format invalid)
     */
    public Provider toProvider() {
        return Provider.from(this);
    }
}
