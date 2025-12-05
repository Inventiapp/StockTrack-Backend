package com.inventiapp.stocktrack.iam.interfaces.rest.transform;

import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembler to convert Role entity to RoleResource
 */
public class RoleResourceFromEntityAssembler {

    /**
     * Convert role entity to role resource
     * @param role The role entity
     * @return RoleResource
     */
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(
                role.getId(),
                role.getName().name()
        );
    }
}

