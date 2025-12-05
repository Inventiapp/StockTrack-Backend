package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembler to convert a Role entity to a RoleResource.
 */
public class RoleResourceFromEntityAssembler {
    /**
     * Converts a Role entity to a RoleResource.
     * @param role The {@link Role} entity to convert.
     * @return The {@link RoleResource} resource.
     */
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(
                role.getId(),
                role.getName().name()
        );
    }
}

