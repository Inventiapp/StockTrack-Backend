package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.commands.SignInCommand;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler to convert a SignInResource to a SignInCommand.
 */
public class SignInCommandFromResourceAssembler {
    /**
     * Converts a SignInResource to a SignInCommand.
     * @param resource The {@link SignInResource} resource to convert.
     * @return The {@link SignInCommand} command.
     */
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }
}

