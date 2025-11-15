package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateProviderResource;

/**
 * Assembler to convert a CreateProviderResource into a CreateProviderCommand.
 */
public final class CreateProviderCommandFromResourceAssembler {

    private CreateProviderCommandFromResourceAssembler() { /* utility */ }

    /**
     * Converts a CreateProviderResource to a CreateProviderCommand.
     *
     * @param resource the incoming create resource (must not be null)
     * @return the CreateProviderCommand
     * @throws IllegalArgumentException if resource is null
     */
    public static CreateProviderCommand toCommandFromResource(CreateProviderResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("CreateProviderResource cannot be null");
        }

        return new CreateProviderCommand(
                resource.firstName(),
                resource.lastName(),
                resource.phoneNumber(),
                resource.email(),
                resource.ruc()
        );
    }
}
