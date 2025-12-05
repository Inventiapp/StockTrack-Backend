package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.commands.SignUpCommand;
import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembler to convert SignUpResource to SignUpCommand
 */
public class SignUpCommandFromResourceAssembler {

    /**
     * Convert resource to command
     * @param resource The sign-up resource
     * @return SignUpCommand
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        List<Role> roles = new ArrayList<>();
        
        if (resource.roles() != null && !resource.roles().isEmpty()) {
            roles = resource.roles().stream()
                    .map(Role::toRoleFromName)
                    .toList();
        }
        
        return new SignUpCommand(resource.email(), resource.password(), roles);
    }
}

