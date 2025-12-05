package com.inventiapp.stocktrack.userpermission.domain.services;

import com.inventiapp.stocktrack.userpermission.domain.model.aggregates.Permission;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetAllPermissionQuery;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetPermissionByIdQuery;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetPermissionByNameQuery;

import java.util.List;
import java.util.Optional;

public interface PermissionQueryService {
    /**
     * Manejador para la consulta GetAllPermissionQuery.
     * @return Una lista de todas las entidades Permission.
     */
    List<Permission> handle(GetAllPermissionQuery query);

    /**
     * Manejador para la consulta GetPermissionByIdQuery.
     * @return Un Optional que puede contener la entidad Permission si se encuentra.
     */
    Optional<Permission> handle(GetPermissionByIdQuery query);

    /**
     * Manejador para la consulta GetPermissionByNameQuery.
     * @return Un Optional que puede contener la entidad Permission si se encuentra.
     */
    Optional<Permission> handle(GetPermissionByNameQuery query);
}