package com.inventiapp.stocktrack.userpermission.domain.model.commands;

import jakarta.validation.constraints.NotBlank;

/**
 * Record que representa el comando para crear un nuevo permiso.
 * @param name El nombre del permiso (debe ser único).
 * @param description Una breve descripción del permiso.
 */
public record CreatePermissionCommand(
    @NotBlank String name,
    @NotBlank String description
) {
}