package com.inventiapp.stocktrack.iam.domain.services;

import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetAllRolesQuery;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for role query operations
 */
public interface RoleQueryService {

    /**
     * Handle get all roles query
     * @param query The query
     * @return List of all roles
     */
    List<Role> handle(GetAllRolesQuery query);

    /**
     * Handle get role by name query
     * @param query The query with role name
     * @return Optional Role
     */
    Optional<Role> handle(GetRoleByNameQuery query);
}

