package com.inventiapp.stocktrack.userpermission.interfaces.rest.transform;

import com.inventiapp.stocktrack.userpermission.domain.model.commands.CreatePermissionCommand;
import com.inventiapp.stocktrack.userpermission.interfaces.rest.resources.CreatePermissionResource;

public class CreatePermissionCommandFromResourceAssembler {
    
    public static CreatePermissionCommand toCommandFromResource(CreatePermissionResource resource) {
        return new CreatePermissionCommand(resource.name(), resource.description());
    }
}