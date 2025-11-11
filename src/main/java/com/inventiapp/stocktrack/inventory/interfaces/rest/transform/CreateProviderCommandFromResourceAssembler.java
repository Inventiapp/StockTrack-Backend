package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateProviderResource;

/**
 * CreateProviderCommandFromResourceAssembler
 * @summary
 * Maps CreateProviderResource (API) to CreateProviderCommand (domain/application).
 * This centralizes the mapping and keeps controllers thin.
 * @since 1.0
 */
public final class CreateProviderCommandFromResourceAssembler {

    private CreateProviderCommandFromResourceAssembler() { }

    /**
     * Convert a CreateProviderResource to CreateProviderCommand.
     *
     * @param resource incoming API resource
     * @return CreateProviderCommand with raw strings (value object creation happens in domain)
     */
    public static CreateProviderCommand toCommandFromResource(CreateProviderResource resource) {
        if (resource == null) return null;
        return new CreateProviderCommand(
                resource.firstName(),
                resource.lastName(),
                resource.phoneNumber(),
                resource.email(),
                resource.ruc()
        );
    }
}
