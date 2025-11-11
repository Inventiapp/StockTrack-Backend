package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.ProviderResource;

import java.util.Date;

/**
 * ProviderResourceFromEntityAssembler
 * @summary
 * Converts Provider domain aggregates to ProviderResource DTOs for the REST layer.
 * @since 1.0
 */
public final class ProviderResourceFromEntityAssembler {

    private ProviderResourceFromEntityAssembler() { }

    /**
     * Convert a Provider aggregate to a ProviderResource.
     *
     * @param provider domain Provider aggregate
     * @return ProviderResource suitable for API responses
     */
    public static ProviderResource toResourceFromEntity(Provider provider) {
        if (provider == null) return null;

        String email = provider.getEmail() == null ? null : provider.getEmail().getValue();
        String ruc = provider.getRuc() == null ? null : provider.getRuc().getValue();
        String phone = provider.getPhoneNumber() == null ? null : provider.getPhoneNumber().getValue();

        Date createdAt = provider.getCreatedAt();
        Date updatedAt = provider.getUpdatedAt();

        return new ProviderResource(
                provider.getId(),
                provider.getFirstName(),
                provider.getLastName(),
                phone,
                email,
                ruc,
                createdAt,
                updatedAt
        );
    }
}
