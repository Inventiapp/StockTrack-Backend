package com.inventiapp.stocktrack.userpermission.domain.services;

import com.inventiapp.stocktrack.userpermission.domain.model.commands.CreatePermissionCommand;
import java.util.Optional;

public interface PermissionCommandService {
    /**
     * Manejador para el comando CreatePermissionCommand.
     * @return El ID del permiso creado (Long).
     */
    Long handle(CreatePermissionCommand command);
}