package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.commands.SignUpCommand;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.SignUpResource;

/**
 * Assembler to convert a SignUpResource to a SignUpCommand.
 */
public class SignUpCommandFromResourceAssembler {
    /**
     * Converts a SignUpResource to a SignUpCommand.
     * @param resource The {@link SignUpResource} resource to convert.
     * @return The {@link SignUpCommand} command.
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.email(), resource.password());
    }
}

