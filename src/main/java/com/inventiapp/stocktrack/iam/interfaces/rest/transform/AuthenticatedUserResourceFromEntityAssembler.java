package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Assembler to convert a User entity to an AuthenticatedUserResource.
 */
public class AuthenticatedUserResourceFromEntityAssembler {
    /**
     * Converts a User entity and token to an AuthenticatedUserResource.
     * @param user The {@link User} entity to convert.
     * @param token The JWT token
     * @return The {@link AuthenticatedUserResource} resource.
     */
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(
                user.getId(),
                user.getEmail(),
                token
        );
    }
}

