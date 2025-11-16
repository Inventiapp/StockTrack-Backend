package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateCategoryCommand;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateCategoryResource;

/**
 * Assembler to create a CreateCategoryCommand from a CreateCategoryResource.
 * @since 1.0
 */
public class CreateCategoryCommandFromResourceAssembler {
    /**
     * Converts a CreateCategoryResource to a CreateCategoryCommand.
     * @param resource CreateCategoryResource to convert
     * @return CreateCategoryCommand created from the resource
     */
    public static CreateCategoryCommand toCommandFromResource(CreateCategoryResource resource) {
        return new CreateCategoryCommand(resource.name(), resource.description());
    }
}

