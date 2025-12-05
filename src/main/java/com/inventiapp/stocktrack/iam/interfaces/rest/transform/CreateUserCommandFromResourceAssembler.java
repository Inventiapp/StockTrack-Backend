package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.commands.CreateUserCommand;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.CreateUserResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembler to convert a CreateUserResource to a CreateUserCommand.
 */
public class CreateUserCommandFromResourceAssembler {
    /**
     * Converts a CreateUserResource to a CreateUserCommand.
     * @param resource The {@link CreateUserResource} resource to convert.
     * @return The {@link CreateUserCommand} command.
     */
    public static CreateUserCommand toCommandFromResource(CreateUserResource resource) {
        List<String> permissions = resource.permissions() != null 
                ? new ArrayList<>(resource.permissions()) 
                : new ArrayList<>();
        
        return new CreateUserCommand(resource.email(), resource.password(), permissions);
    }
}

