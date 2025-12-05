package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.UserResource;

/**
 * Assembler to convert User entity to UserResource
 */
public class UserResourceFromEntityAssembler {

    /**
     * Convert user entity to user resource
     * @param user The user entity
     * @return UserResource
     */
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getEmail(),
                user.getRolesAsStringList()
        );
    }
}

