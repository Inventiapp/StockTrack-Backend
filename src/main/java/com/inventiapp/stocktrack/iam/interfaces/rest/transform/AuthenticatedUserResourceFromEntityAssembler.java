package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Assembler to convert User entity to AuthenticatedUserResource
 */
public class AuthenticatedUserResourceFromEntityAssembler {

    /**
     * Convert user and token to authenticated user resource
     * @param user The user entity
     * @param token The JWT token
     * @return AuthenticatedUserResource
     */
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(
                user.getId(),
                user.getEmail(),
                token
        );
    }
}

