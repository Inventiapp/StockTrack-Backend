package com.inventiapp.stocktrack.userpermission.interfaces.rest.transform;

import com.inventiapp.stocktrack.userpermission.domain.model.aggregates.Permission;
import com.inventiapp.stocktrack.userpermission.interfaces.rest.resources.PermissionResource;

public class PermissionResourceFromEntityAssembler {

    public static PermissionResource toResourceFromEntity(Permission entity) {
        return new PermissionResource(entity.getId(), entity.getName(), entity.getDescription());
    }
}