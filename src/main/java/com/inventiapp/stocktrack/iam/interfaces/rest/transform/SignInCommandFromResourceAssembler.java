package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.commands.SignInCommand;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler to convert SignInResource to SignInCommand
 */
public class SignInCommandFromResourceAssembler {

    /**
     * Convert resource to command
     * @param resource The sign-in resource
     * @return SignInCommand
     */
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }
}

