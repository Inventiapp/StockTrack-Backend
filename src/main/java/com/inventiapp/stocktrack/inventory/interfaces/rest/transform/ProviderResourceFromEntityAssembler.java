package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.ProviderResource;

/**
 * Assembler to convert Provider aggregate into ProviderResource for API responses.
 * It reads the Provider fields and unwraps embedded value objects safely.
 */
public class ProviderResourceFromEntityAssembler {
    /**
     * Converts a Provider entity into a ProviderResource.
     * Null-safely unwraps embedded value objects.
     *
     * @param provider the provider aggregate (must not be null)
     * @return ProviderResource containing public provider data
     */
    public static ProviderResource toResource(Provider provider) {
        if (provider == null) {
            return null;
        }

        String phone = null;
        if (provider.getPhoneNumber() != null) {
            // PhoneNumber record exposes 'number()'
            phone = provider.getPhoneNumber().number();
        }

        String email = null;
        if (provider.getEmail() != null) {
            // Email record exposes 'address()'
            email = provider.getEmail().address();
        }

        String ruc = null;
        if (provider.getRuc() != null) {
            // Ruc record exposes 'value()'
            ruc = provider.getRuc().value();
        }

        return new ProviderResource(
                provider.getId(),
                provider.getFirstName(),
                provider.getLastName(),
                phone,
                email,
                ruc
        );
    }
}
