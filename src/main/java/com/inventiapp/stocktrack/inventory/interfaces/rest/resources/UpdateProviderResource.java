package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

/**
 * Update provider resource.
 */
public record UpdateProviderResource(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String ruc
) {
    public UpdateProviderResource {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (ruc == null || ruc.isBlank()) {
            throw new IllegalArgumentException("RUC is required");
        }
    }
}