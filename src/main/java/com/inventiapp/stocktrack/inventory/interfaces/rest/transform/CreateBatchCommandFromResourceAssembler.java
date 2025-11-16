package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateBatchCommand;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateBatchResource;

/**
 * Assembler that converts a CreateBatchResource into a CreateBatchCommand.
 */
public class CreateBatchCommandFromResourceAssembler {

    private CreateBatchCommandFromResourceAssembler() {}

    /**
     * Converts a CreateBatchResource to a CreateBatchCommand.
     *
     * @param resource the incoming resource from the API
     * @return the command ready to be handled by the application layer
     * @throws IllegalArgumentException if resource is null
     */
    public static CreateBatchCommand toCommandFromResource(CreateBatchResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("CreateBatchResource cannot be null");
        }

        return new CreateBatchCommand(
                resource.productId(),
                resource.quantity(),
                resource.expirationDate(),
                resource.receptionDate()
        );
    }
}

