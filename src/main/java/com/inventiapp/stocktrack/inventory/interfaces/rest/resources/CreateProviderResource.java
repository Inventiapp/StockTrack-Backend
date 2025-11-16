package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

/**
 * Resource used to create a Provider.
 * Validates the incoming payload at construction time.
 *
 * @param firstName   provider first name (required)
 * @param lastName    provider last name (required)
 * @param phoneNumber provider phone number (required)
 * @param email       provider email (required)
 * @param ruc         provider RUC (required)
 */
public record CreateProviderResource(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String ruc
) {
    /**
     * Validate the resource.
     *
     * @throws IllegalArgumentException if any required field is null or blank.
     */
    public CreateProviderResource {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName is required");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("phoneNumber is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (ruc == null || ruc.isBlank()) {
            throw new IllegalArgumentException("ruc is required");
        }
    }
}
