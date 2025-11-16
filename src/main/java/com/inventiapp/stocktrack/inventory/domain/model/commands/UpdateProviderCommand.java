package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * Command to update a provider
 *
 * @param providerId  the provider id. Cannot be null or less than 1
 * @param firstName   the provider first name. Cannot be null or blank
 * @param lastName    the provider last name.  Cannot be null or blank
 * @param phoneNumber the provider phone number. Cannot be null or blank
 * @param email       the provider email. Cannot be null or blank
 * @param ruc         the provider RUC. Cannot be null or blank
 */
public record UpdateProviderCommand(
        Long providerId,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String ruc
) {
    /**
     * Constructor
     *
     * @throws IllegalArgumentException if providerId is null or less than 1
     * @throws IllegalArgumentException if any required field is null or blank
     */
    public UpdateProviderCommand {
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("providerId cannot be null or less than 1");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be null or blank");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("phoneNumber cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be null or blank");
        }
        if (ruc == null || ruc.isBlank()) {
            throw new IllegalArgumentException("ruc cannot be null or blank");
        }
    }
}
