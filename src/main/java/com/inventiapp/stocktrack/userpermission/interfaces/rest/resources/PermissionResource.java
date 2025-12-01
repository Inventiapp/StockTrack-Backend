package com.inventiapp.stocktrack.userpermission.interfaces.rest.resources;

// Este record es el DTO que se envía como respuesta.
// Mapea la entidad Permission a un JSON limpio.
public record PermissionResource(
    Long id,
    String name,
    String description
) {
}