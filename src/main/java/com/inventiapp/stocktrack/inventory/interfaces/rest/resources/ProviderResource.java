package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

import java.util.Date;

/**
 * ProviderResource
 * @summary
 * This record represents the API representation of a Provider returned by the REST endpoints.
 * It includes audit fields createdAt and updatedAt coming from the aggregate base class.
 * @since 1.0
 */
public record ProviderResource(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String ruc,
        Date createdAt,
        Date updatedAt
) { }
