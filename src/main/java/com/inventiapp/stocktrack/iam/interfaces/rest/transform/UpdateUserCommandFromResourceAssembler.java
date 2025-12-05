package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.commands.UpdateUserCommand;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.UpdateUserResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembler to convert an UpdateUserResource to an UpdateUserCommand.
 */
public class UpdateUserCommandFromResourceAssembler {
    /**
     * Converts an UpdateUserResource to an UpdateUserCommand.
     * @param userId The user ID to update
     * @param resource The {@link UpdateUserResource} resource to convert.
     * @return The {@link UpdateUserCommand} command.
     */
    public static UpdateUserCommand toCommandFromResource(Long userId, UpdateUserResource resource) {
        List<String> permissions = resource.permissions() != null 
                ? new ArrayList<>(resource.permissions()) 
                : new ArrayList<>();
        
        return new UpdateUserCommand(userId, resource.email(), resource.password(), permissions);
    }
}

